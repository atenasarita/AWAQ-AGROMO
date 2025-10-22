package com.example.awaq_agromo.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    // Doc: https://openweathermap.org/api/one-call-3
    @GET("data/3.0/onecall")
    suspend fun oneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely,alerts",
        @Query("units") units: String = "metric", // °C, m/s
        @Query("appid") apiKey: String
    ): OneCallResponse
}