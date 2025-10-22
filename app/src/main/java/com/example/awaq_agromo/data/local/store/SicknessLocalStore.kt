package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("sickness_store")

object SicknessLocalStore {

    private val KEY_PRESENCE = stringPreferencesKey("sickness_presence")
    private val KEY_DAMAGE = stringPreferencesKey("sickness_damage")
    private val KEY_SEVERITY = stringPreferencesKey("sickness_severity")
    private val KEY_PARTS = stringPreferencesKey("sickness_parts")
    private val KEY_AFFECT = stringPreferencesKey("sickness_affect_level")
    private val KEY_OBS = stringPreferencesKey("sickness_observations")

    suspend fun savePresence(context: Context, value: String) {
        context.dataStore.edit { it[KEY_PRESENCE] = value }
    }

    suspend fun saveDamage(context: Context, value: String) {
        context.dataStore.edit { it[KEY_DAMAGE] = value }
    }

    suspend fun saveSeverity(context: Context, value: String) {
        context.dataStore.edit { it[KEY_SEVERITY] = value }
    }

    suspend fun saveParts(context: Context, value: String) {
        context.dataStore.edit { it[KEY_PARTS] = value }
    }

    suspend fun saveAffectLevel(context: Context, value: String) {
        context.dataStore.edit { it[KEY_AFFECT] = value }
    }

    suspend fun saveObservations(context: Context, value: String) {
        context.dataStore.edit { it[KEY_OBS] = value }
    }

    fun readPresence(context: Context) = context.dataStore.data.map { it[KEY_PRESENCE] ?: "" }
    fun readDamage(context: Context) = context.dataStore.data.map { it[KEY_DAMAGE] ?: "" }
    fun readSeverity(context: Context) = context.dataStore.data.map { it[KEY_SEVERITY] ?: "" }
    fun readParts(context: Context) = context.dataStore.data.map { it[KEY_PARTS] ?: "" }
    fun readAffectLevel(context: Context) = context.dataStore.data.map { it[KEY_AFFECT] ?: "Todo bien" }
    fun readObservations(context: Context) = context.dataStore.data.map { it[KEY_OBS] ?: "" }
}