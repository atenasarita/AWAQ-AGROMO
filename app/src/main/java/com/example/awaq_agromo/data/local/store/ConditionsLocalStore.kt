package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("conditions_store")

object ConditionsLocalStore {
    private val KEY_FERTILITY = stringPreferencesKey("fertility_level")
    private val KEY_N = stringPreferencesKey("nitrogen_value")
    private val KEY_P = stringPreferencesKey("phosphorus_value")
    private val KEY_K = stringPreferencesKey("potassium_value")
    private val KEY_MO = stringPreferencesKey("organic_matter_value")
    private val KEY_CIC = stringPreferencesKey("cic_value")

    suspend fun saveFertilityLevel(context: Context, value: String) {
        context.dataStore.edit { it[KEY_FERTILITY] = value }
    }

    suspend fun saveNitrogen(context: Context, value: String) {
        context.dataStore.edit { it[KEY_N] = value }
    }

    suspend fun savePhosphorus(context: Context, value: String) {
        context.dataStore.edit { it[KEY_P] = value }
    }

    suspend fun savePotassium(context: Context, value: String) {
        context.dataStore.edit { it[KEY_K] = value }
    }

    suspend fun saveOrganicMatter(context: Context, value: String) {
        context.dataStore.edit { it[KEY_MO] = value }
    }

    suspend fun saveCIC(context: Context, value: String) {
        context.dataStore.edit { it[KEY_CIC] = value }
    }

    fun readFertilityLevel(context: Context) =
        context.dataStore.data.map { it[KEY_FERTILITY] }

    fun readNitrogen(context: Context) =
        context.dataStore.data.map { it[KEY_N] }

    fun readPhosphorus(context: Context) =
        context.dataStore.data.map { it[KEY_P] }

    fun readPotassium(context: Context) =
        context.dataStore.data.map { it[KEY_K] }

    fun readOrganicMatter(context: Context) =
        context.dataStore.data.map { it[KEY_MO] }

    fun readCIC(context: Context) =
        context.dataStore.data.map { it[KEY_CIC] }
}