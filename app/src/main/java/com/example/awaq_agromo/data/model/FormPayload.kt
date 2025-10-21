package com.example.awaq_agromo.data.model

data class CropBlock(
    val cropsSelected: List<String>,
    val sowingDateMillis: Long? // Date?.time (UTC)
)

data class HumidityBlock(
    val manualScale: String?, // enum name o texto ("MuySeco", etc.)
    val sensorValue: Int?     // 0..100
)

data class PhBlock(
    val value: Int?,     // 0..14
    val method: String?  // "Digital" | "Reactiva" | "Manual"
)

data class WeatherBlock(
    val locationLabel: String?,
    val humidityPct: Int?,
    val windKmh: Int?,
    val rainPct: Int?,
    val tempC: Int?,
    val timestamp: Long?
)

data class FormPayload(
    val crop: CropBlock,
    val humidity: HumidityBlock,
    val ph: PhBlock,
    val weather: WeatherBlock? = null // opcional
)