package com.example.awaq_agromo.data.remote.api


import com.squareup.moshi.Json

data class OneCallResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String?,
    @Json(name = "timezone_offset") val timezoneOffset: Long?,
    val current: Current?,
    val hourly: List<Hourly>?
)

data class Current(
    val dt: Long,
    val temp: Double,        // vendrá en °C si pasamos units=metric
    val humidity: Int,       // %
    @Json(name = "wind_speed") val windSpeed: Double,  // m/s
    val clouds: Int? = null, // % de nubosidad
    val rain: Map<String, Double>? = null // puede traer "1h": mm
)

data class Hourly(
    val dt: Long,
    val temp: Double,
    val humidity: Int,
    @Json(name = "wind_speed") val windSpeed: Double,
    val pop: Double? = null, // prob. precipitación [0..1]
    val rain: Map<String, Double>? = null
)