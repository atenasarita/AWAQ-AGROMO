package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("states_store")

object StatesLocalStore {

    private val KEY_PLANT_STATE = stringPreferencesKey("plant_state")
    private val KEY_OBSERVATIONS = stringPreferencesKey("plant_observations")

    suspend fun savePlantState(context: Context, value: String) {
        context.dataStore.edit { it[KEY_PLANT_STATE] = value }
    }

    suspend fun saveObservations(context: Context, value: String) {
        context.dataStore.edit { it[KEY_OBSERVATIONS] = value }
    }

    fun readPlantState(context: Context) =
        context.dataStore.data.map { it[KEY_PLANT_STATE] ?: "" }

    fun readObservations(context: Context) =
        context.dataStore.data.map { it[KEY_OBSERVATIONS] ?: "" }
}
