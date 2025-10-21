package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.minus
import kotlin.collections.plus
import kotlin.text.isEmpty
import kotlin.text.trim

private const val DS_NAME = "crop_store"
private val Context.cropDataStore by preferencesDataStore(DS_NAME)

object CropLocalStore {
    private val KEY_SELECTED = stringSetPreferencesKey("crops_selected")
    private val KEY_SOWING_DATE = stringPreferencesKey("sowing_date")

    /** Lee los cultivos seleccionados */
    fun readSelected(ctx: Context): Flow<Set<String>> =
        ctx.cropDataStore.data.map { it[KEY_SELECTED] ?: emptySet() }

    /** Guarda todos los cultivos */
    suspend fun saveSelected(ctx: Context, items: Set<String>) {
        ctx.cropDataStore.edit { prefs ->
            prefs[KEY_SELECTED] = items
        }
    }

    /** Agrega un cultivo */
    suspend fun addOne(ctx: Context, name: String) {
        val clean = name.trim()
        if (clean.isEmpty()) return
        ctx.cropDataStore.edit { prefs ->
            val current = prefs[KEY_SELECTED] ?: emptySet()
            prefs[KEY_SELECTED] = current + clean
        }
    }

    /** Quita un cultivo */
    suspend fun removeOne(ctx: Context, name: String) {
        ctx.cropDataStore.edit { prefs ->
            val current = prefs[KEY_SELECTED] ?: emptySet()
            prefs[KEY_SELECTED] = current - name
        }
    }

    /** Alterna (agrega o quita) */
    suspend fun toggle(ctx: Context, name: String) {
        ctx.cropDataStore.edit { prefs ->
            val current = prefs[KEY_SELECTED] ?: emptySet()
            prefs[KEY_SELECTED] = if (current.contains(name)) current - name else current + name
        }
    }

    /** Guarda la fecha de siembra */
    suspend fun saveSowingDate(ctx: Context, date: String) {
        ctx.cropDataStore.edit { prefs ->
            prefs[KEY_SOWING_DATE] = date
        }
    }

    /** Lee la fecha de siembra */
    fun readSowingDate(ctx: Context): Flow<String?> =
        ctx.cropDataStore.data.map { it[KEY_SOWING_DATE] }
}