package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class CultivosUsuario @Inject constructor(
    @ApplicationContext private val context: Context
) {    private val Context.dataStore by preferencesDataStore("crop_prefs")
    private fun userCropsKey(userId: String) = stringSetPreferencesKey("crops_$userId")

    fun readSelected(userId: String): Flow<Set<String>> = context.dataStore.data
        .map { prefs -> prefs[userCropsKey(userId)] ?: emptySet() }

    suspend fun addOne(userId: String, crop: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[userCropsKey(userId)] ?: emptySet()
            prefs[userCropsKey(userId)] = current + crop
        }
    }

    suspend fun removeOne(userId: String, crop: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[userCropsKey(userId)] ?: emptySet()
            prefs[userCropsKey(userId)] = current - crop
        }
    }

    suspend fun toggle(userId: String, crop: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[userCropsKey(userId)] ?: emptySet()
            prefs[userCropsKey(userId)] =
                if (current.contains(crop)) current - crop else current + crop
        }
    }

    suspend fun clearAll(userId: String) {
        context.dataStore.edit { prefs ->
            prefs[userCropsKey(userId)] = emptySet()
        }
    }
}
