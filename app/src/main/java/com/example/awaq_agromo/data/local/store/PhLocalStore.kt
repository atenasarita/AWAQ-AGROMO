package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DS_NAME = "ph_store"
private val Context.phDataStore by preferencesDataStore(DS_NAME)

object PhLocalStore {
    private val KEY_PH_VALUE  = stringPreferencesKey("ph_value")   // "6.5"
    private val KEY_PH_METHOD = stringPreferencesKey("ph_method")  // "Digital | Cinta | Manual"

    fun readPhValue(ctx: Context): Flow<String?> =
        ctx.phDataStore.data.map { it[KEY_PH_VALUE] }

    fun readPhMethod(ctx: Context): Flow<String?> =
        ctx.phDataStore.data.map { it[KEY_PH_METHOD] }

    suspend fun savePhValue(ctx: Context, value: String) {
        ctx.phDataStore.edit { it[KEY_PH_VALUE] = value }
    }

    suspend fun savePhMethod(ctx: Context, method: String) {
        ctx.phDataStore.edit { it[KEY_PH_METHOD] = method }
    }
}