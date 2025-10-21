package com.example.awaq_agromo.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import android.provider.CallLog.Locations.LONGITUDE

// Create a single DataStore instance
private val Context.dataStore by preferencesDataStore("app_prefs")

@Singleton
class DataStoreManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

        private val LATITUDE_KEY = doublePreferencesKey("latitude")
        private val LONGITUDE_KEY = doublePreferencesKey("longitude")

        val LATITUDE = doublePreferencesKey("latitude")
        val LONGITUDE = doublePreferencesKey("longitude")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    val token: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[TOKEN_KEY]
    }

    suspend fun clearToken() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ONBOARDING_COMPLETED] = completed
        }
    }

    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[ONBOARDING_COMPLETED] ?: false
    }

    suspend fun saveLocation(latitude: Double, longitude: Double) {
        context.dataStore.edit { prefs ->
            prefs[LATITUDE_KEY] = latitude
            prefs[LONGITUDE_KEY] = longitude
        }
    }

    fun getLocation(): Flow<Pair<Double, Double>?> = context.dataStore.data.map { prefs ->
        val lat = prefs[LATITUDE]
        val lon = prefs[LONGITUDE]
        if (lat != null && lon != null) Pair(lat, lon) else null
    }
}