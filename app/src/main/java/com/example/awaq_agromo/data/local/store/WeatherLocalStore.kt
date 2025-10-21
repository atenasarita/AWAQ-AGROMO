package com.example.awaq_agromo.data.local.store


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DS_NAME = "weather_store"
private val Context.weatherDataStore by preferencesDataStore(DS_NAME)

data class WeatherSnapshot(
    val locationLabel: String,
    val humidityPct: Int,
    val windKmh: Int,
    val rainPct: Int,
    val tempC: Int,
    val timestamp: Long
)

object WeatherLocalStore {

    // Keys tipadas por ubicación
    private fun kLoc(loc: String) = stringPreferencesKey("loc_$loc")
    private fun kHum(loc: String) = intPreferencesKey("hum_$loc")
    private fun kWin(loc: String) = intPreferencesKey("win_$loc")
    private fun kRai(loc: String) = intPreferencesKey("rai_$loc")
    private fun kTmp(loc: String) = intPreferencesKey("tmp_$loc")
    private fun kTs(loc: String)  = longPreferencesKey("ts_$loc")

    suspend fun saveSnapshot(ctx: Context, snap: WeatherSnapshot) {
        val label = snap.locationLabel
        ctx.weatherDataStore.edit { p ->
            p[kLoc(label)] = label
            p[kHum(label)] = snap.humidityPct
            p[kWin(label)] = snap.windKmh
            p[kRai(label)] = snap.rainPct
            p[kTmp(label)] = snap.tempC
            p[kTs(label)]  = snap.timestamp
        }
    }

    fun readSnapshot(ctx: Context, locationLabel: String): Flow<WeatherSnapshot?> =
        ctx.weatherDataStore.data.map { p ->
            val loc = p[kLoc(locationLabel)] ?: return@map null
            val hum = p[kHum(loc)] ?: return@map null
            val win = p[kWin(loc)] ?: return@map null
            val rai = p[kRai(loc)] ?: return@map null
            val tmp = p[kTmp(loc)] ?: return@map null
            val ts  = p[kTs(loc)]  ?: 0L
            WeatherSnapshot(loc, hum, win, rai, tmp, ts)
        }
}