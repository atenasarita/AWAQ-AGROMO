package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DS_NAME = "humedad_stsore"
private val Context.humedadDataStore by preferencesDataStore(DS_NAME)

object HumedadLocalStore {
    private val KEY_MANUAL_SCALE = stringPreferencesKey("manual_scale")   // ej. "Húmedo"
    private val KEY_SENSOR_VALUE = stringPreferencesKey("sensor_value")   // ej. "23"

    fun readManualScale(ctx: Context): Flow<String?> =
        ctx.humedadDataStore.data.map { it[KEY_MANUAL_SCALE] }

    fun readSensorValue(ctx: Context): Flow<String?> =
        ctx.humedadDataStore.data.map { it[KEY_SENSOR_VALUE] }

    suspend fun saveManualScale(ctx: Context, value: String) {
        ctx.humedadDataStore.edit { it[KEY_MANUAL_SCALE] = value }
    }

    suspend fun saveSensorValue(ctx: Context, value: String) {
        ctx.humedadDataStore.edit { it[KEY_SENSOR_VALUE] = value }
    }
}