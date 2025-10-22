package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("plague_store")

object PlagueLocalStore {

    private val KEY_PRESENCE = stringPreferencesKey("plague_presence")
    private val KEY_TYPE = stringPreferencesKey("plague_type")
    private val KEY_PARTS = stringPreferencesKey("affected_parts")
    private val KEY_SEVERITY = stringPreferencesKey("severity_level")
    private val KEY_DAMAGE = stringPreferencesKey("damage_type")
    private val KEY_OBS = stringPreferencesKey("plague_observations")

    suspend fun savePresence(context: Context, value: String) {
        context.dataStore.edit { it[KEY_PRESENCE] = value }
    }

    suspend fun savePlagueType(context: Context, value: String) {
        context.dataStore.edit { it[KEY_TYPE] = value }
    }

    suspend fun saveAffectedParts(context: Context, value: String) {
        context.dataStore.edit { it[KEY_PARTS] = value }
    }

    suspend fun saveSeverity(context: Context, value: String) {
        context.dataStore.edit { it[KEY_SEVERITY] = value }
    }

    suspend fun saveDamage(context: Context, value: String) {
        context.dataStore.edit { it[KEY_DAMAGE] = value }
    }

    suspend fun saveObservations(context: Context, value: String) {
        context.dataStore.edit { it[KEY_OBS] = value }
    }

    fun readPresence(context: Context) = context.dataStore.data.map { it[KEY_PRESENCE] ?: "" }
    fun readPlagueType(context: Context) = context.dataStore.data.map { it[KEY_TYPE] ?: "" }
    fun readAffectedParts(context: Context) = context.dataStore.data.map { it[KEY_PARTS] ?: "" }
    fun readSeverity(context: Context) = context.dataStore.data.map { it[KEY_SEVERITY] ?: "" }
    fun readDamage(context: Context) = context.dataStore.data.map { it[KEY_DAMAGE] ?: "" }
    fun readObservations(context: Context) = context.dataStore.data.map { it[KEY_OBS] ?: "" }
}