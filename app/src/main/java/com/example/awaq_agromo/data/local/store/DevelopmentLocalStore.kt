package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("development_store")

object DevelopmentLocalStore {

    private val KEY_DATE = stringPreferencesKey("measurement_date")
    private val KEY_METHOD = stringPreferencesKey("measurement_method")
    private val KEY_PLANT_NUMBER = stringPreferencesKey("plant_number")
    private val KEY_HEIGHT = stringPreferencesKey("plant_height")

    suspend fun saveDate(context: Context, value: String) {
        context.dataStore.edit { it[KEY_DATE] = value }
    }

    suspend fun saveMethod(context: Context, value: String) {
        context.dataStore.edit { it[KEY_METHOD] = value }
    }

    suspend fun savePlantNumber(context: Context, value: String) {
        context.dataStore.edit { it[KEY_PLANT_NUMBER] = value }
    }

    suspend fun saveHeight(context: Context, value: String) {
        context.dataStore.edit { it[KEY_HEIGHT] = value }
    }

    fun readDate(context: Context) = context.dataStore.data.map { it[KEY_DATE] ?: "" }
    fun readMethod(context: Context) = context.dataStore.data.map { it[KEY_METHOD] ?: "" }
    fun readPlantNumber(context: Context) = context.dataStore.data.map { it[KEY_PLANT_NUMBER] ?: "" }
    fun readHeight(context: Context) = context.dataStore.data.map { it[KEY_HEIGHT] ?: "" }
}
