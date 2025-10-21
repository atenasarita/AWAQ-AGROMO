package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("foliage_store")

object FoliageLocalStore {

    private val KEY_DENSITY = stringPreferencesKey("foliage_density")
    private val KEY_COLOR = stringPreferencesKey("foliage_color")
    private val KEY_CONDITION = stringPreferencesKey("foliage_condition")

    suspend fun saveDensity(context: Context, value: String) {
        context.dataStore.edit { it[KEY_DENSITY] = value }
    }

    suspend fun saveColor(context: Context, value: String) {
        context.dataStore.edit { it[KEY_COLOR] = value }
    }

    suspend fun saveCondition(context: Context, value: String) {
        context.dataStore.edit { it[KEY_CONDITION] = value }
    }

    fun readDensity(context: Context) = context.dataStore.data.map { it[KEY_DENSITY] ?: "" }
    fun readColor(context: Context) = context.dataStore.data.map { it[KEY_COLOR] ?: "" }
    fun readCondition(context: Context) = context.dataStore.data.map { it[KEY_CONDITION] ?: "" }
}
