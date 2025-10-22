package com.example.awaq_agromo.data.remote.api

import com.example.awaq_agromo.data.remote.api.OpenWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OpenWeatherModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApi {
        // Usa el mismo Retrofit que ya inyectas en otros módulos
        return retrofit.create(OpenWeatherApi::class.java)
    }
}