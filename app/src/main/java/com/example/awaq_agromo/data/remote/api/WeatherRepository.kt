package com.example.awaq_agromo.data.remote.api

import android.content.Context
import com.example.awaq_agromo.data.local.store.WeatherLocalStore
import com.example.awaq_agromo.data.local.store.WeatherSnapshot
import com.example.awaq_agromo.data.remote.api.OpenWeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: OpenWeatherApi
) {
    suspend fun fetchAndSaveSnapshot(
        context: Context,
        label: String,   // "Ciudad, Estado" (lo que ya obtienes con Geocoder)
        lat: Double,
        lon: Double,
        apiKey: String
    ): WeatherSnapshot = withContext(Dispatchers.IO) {
        val response = api.oneCall(
            lat = lat,
            lon = lon,
            exclude = "minutely,alerts",
            units = "metric",   // °C y m/s
            apiKey = apiKey
        )

        val current = requireNotNull(response.current) { "OpenWeather current == null" }

        val tempC = current.temp.roundToInt()
        val humidity = current.humidity
        val windKmh = (current.windSpeed * 3.6).roundToInt() // m/s -> km/h

        // Probabilidad de lluvia próxima hora si existe; si no, nubosidad como proxy
        val rainPct = response.hourly
            ?.firstOrNull()
            ?.pop
            ?.let { (it * 100).roundToInt() }
            ?: (current.clouds ?: 0)

        val snap = WeatherSnapshot(
            locationLabel = label,
            humidityPct = humidity,
            windKmh = windKmh,
            rainPct = rainPct,
            tempC = tempC,
            timestamp = System.currentTimeMillis()
        )

        WeatherLocalStore.saveSnapshot(context, snap)
        snap
    }
}