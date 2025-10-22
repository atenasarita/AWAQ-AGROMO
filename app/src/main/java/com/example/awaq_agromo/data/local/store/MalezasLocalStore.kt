package com.example.awaq_agromo.data.local.store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DS_NAME = "malezas_store"
private val Context.malezasDataStore by preferencesDataStore(DS_NAME)

object MalezasLocalStore {
    // Claves para las preferencias
    private val KEY_TIENE_MALEZAS = booleanPreferencesKey("tiene_malezas")

    // Tipos de maleza
    private val KEY_HOJA_ANCHA = booleanPreferencesKey("hoja_ancha")
    private val KEY_RASTRERA_TREPADORA = booleanPreferencesKey("rastrera_trepadora")
    private val KEY_OTRA_NO_SE = booleanPreferencesKey("otra_no_se")
    private val KEY_HOJA_ANGOSTA = booleanPreferencesKey("hoja_angosta")
    private val KEY_ARBUSTIVA_ALTA = booleanPreferencesKey("arbustiva_alta")
    private val KEY_NINGUNA_TIPO = booleanPreferencesKey("ninguna_tipo")

    // Porcentaje de área afectada
    private val KEY_PORCENTAJE_AREA = floatPreferencesKey("porcentaje_area")

    // Dónde se concentran
    private val KEY_EN_LOS_BORDES = booleanPreferencesKey("en_los_bordes")
    private val KEY_EN_ZONAS_DISPERSA = booleanPreferencesKey("en_zonas_dispersas")
    private val KEY_EN_EL_CENTRO = booleanPreferencesKey("en_el_centro")
    private val KEY_EN_TODO_EL_CULTIVO = booleanPreferencesKey("en_todo_el_cultivo")

    // Controles aplicados
    private val KEY_CONTROL_MANUAL = booleanPreferencesKey("control_manual")
    private val KEY_CONTROL_QUIMICO = booleanPreferencesKey("control_quimico")
    private val KEY_CONTROL_COBERTURA = booleanPreferencesKey("control_cobertura")
    private val KEY_NO_CONTROL = booleanPreferencesKey("no_control")

    // URI de la imagen
    private val KEY_IMAGE_URI = stringPreferencesKey("image_uri")

    // Guardar todos los datos de malezas
    suspend fun saveMalezasData(
        ctx: Context,
        tieneMalezas: Boolean,
        hojaAncha: Boolean,
        rastreraTrepadora: Boolean,
        otraNoSe: Boolean,
        hojaAngosta: Boolean,
        arbustivaAlta: Boolean,
        ningunaTipo: Boolean,
        porcentajeArea: Float,
        enLosBordes: Boolean,
        enZonasDispersas: Boolean,
        enElCentro: Boolean,
        enTodoElCultivo: Boolean,
        controlManual: Boolean,
        controlQuimico: Boolean,
        controlCobertura: Boolean,
        noControl: Boolean,
        imageUri: String? = null
    ) {
        ctx.malezasDataStore.edit { prefs ->
            prefs[KEY_TIENE_MALEZAS] = tieneMalezas

            prefs[KEY_HOJA_ANCHA] = hojaAncha
            prefs[KEY_RASTRERA_TREPADORA] = rastreraTrepadora
            prefs[KEY_OTRA_NO_SE] = otraNoSe
            prefs[KEY_HOJA_ANGOSTA] = hojaAngosta
            prefs[KEY_ARBUSTIVA_ALTA] = arbustivaAlta
            prefs[KEY_NINGUNA_TIPO] = ningunaTipo

            prefs[KEY_PORCENTAJE_AREA] = porcentajeArea

            prefs[KEY_EN_LOS_BORDES] = enLosBordes
            prefs[KEY_EN_ZONAS_DISPERSA] = enZonasDispersas
            prefs[KEY_EN_EL_CENTRO] = enElCentro
            prefs[KEY_EN_TODO_EL_CULTIVO] = enTodoElCultivo

            prefs[KEY_CONTROL_MANUAL] = controlManual
            prefs[KEY_CONTROL_QUIMICO] = controlQuimico
            prefs[KEY_CONTROL_COBERTURA] = controlCobertura
            prefs[KEY_NO_CONTROL] = noControl

            imageUri?.let { prefs[KEY_IMAGE_URI] = it }
        }
    }

    // Leer todos los datos de malezas
    fun readMalezasData(ctx: Context): Flow<MalezasData> =
        ctx.malezasDataStore.data.map { prefs ->
            MalezasData(
                tieneMalezas = prefs[KEY_TIENE_MALEZAS] ?: true,
                hojaAncha = prefs[KEY_HOJA_ANCHA] ?: false,
                rastreraTrepadora = prefs[KEY_RASTRERA_TREPADORA] ?: false,
                otraNoSe = prefs[KEY_OTRA_NO_SE] ?: false,
                hojaAngosta = prefs[KEY_HOJA_ANGOSTA] ?: false,
                arbustivaAlta = prefs[KEY_ARBUSTIVA_ALTA] ?: false,
                ningunaTipo = prefs[KEY_NINGUNA_TIPO] ?: false,
                porcentajeArea = prefs[KEY_PORCENTAJE_AREA] ?: 0f,
                enLosBordes = prefs[KEY_EN_LOS_BORDES] ?: false,
                enZonasDispersas = prefs[KEY_EN_ZONAS_DISPERSA] ?: false,
                enElCentro = prefs[KEY_EN_EL_CENTRO] ?: false,
                enTodoElCultivo = prefs[KEY_EN_TODO_EL_CULTIVO] ?: false,
                controlManual = prefs[KEY_CONTROL_MANUAL] ?: false,
                controlQuimico = prefs[KEY_CONTROL_QUIMICO] ?: false,
                controlCobertura = prefs[KEY_CONTROL_COBERTURA] ?: false,
                noControl = prefs[KEY_NO_CONTROL] ?: false,
                imageUri = prefs[KEY_IMAGE_URI]
            )
        }

    // Limpiar todos los datos de malezas
    suspend fun clearMalezasData(ctx: Context) {
        ctx.malezasDataStore.edit { prefs ->
            prefs.remove(KEY_TIENE_MALEZAS)
            prefs.remove(KEY_HOJA_ANCHA)
            prefs.remove(KEY_RASTRERA_TREPADORA)
            prefs.remove(KEY_OTRA_NO_SE)
            prefs.remove(KEY_HOJA_ANGOSTA)
            prefs.remove(KEY_ARBUSTIVA_ALTA)
            prefs.remove(KEY_NINGUNA_TIPO)
            prefs.remove(KEY_PORCENTAJE_AREA)
            prefs.remove(KEY_EN_LOS_BORDES)
            prefs.remove(KEY_EN_ZONAS_DISPERSA)
            prefs.remove(KEY_EN_EL_CENTRO)
            prefs.remove(KEY_EN_TODO_EL_CULTIVO)
            prefs.remove(KEY_CONTROL_MANUAL)
            prefs.remove(KEY_CONTROL_QUIMICO)
            prefs.remove(KEY_CONTROL_COBERTURA)
            prefs.remove(KEY_NO_CONTROL)
            prefs.remove(KEY_IMAGE_URI)
        }
    }

    // Guardar solo la URI de la imagen
    suspend fun saveImageUri(ctx: Context, imageUri: String) {
        ctx.malezasDataStore.edit { prefs ->
            prefs[KEY_IMAGE_URI] = imageUri
        }
    }

    // Leer solo la URI de la imagen
    fun readImageUri(ctx: Context): Flow<String?> =
        ctx.malezasDataStore.data.map { it[KEY_IMAGE_URI] }
}

// Data class para contener todos los datos de malezas
data class MalezasData(
    val tieneMalezas: Boolean,
    val hojaAncha: Boolean,
    val rastreraTrepadora: Boolean,
    val otraNoSe: Boolean,
    val hojaAngosta: Boolean,
    val arbustivaAlta: Boolean,
    val ningunaTipo: Boolean,
    val porcentajeArea: Float,
    val enLosBordes: Boolean,
    val enZonasDispersas: Boolean,
    val enElCentro: Boolean,
    val enTodoElCultivo: Boolean,
    val controlManual: Boolean,
    val controlQuimico: Boolean,
    val controlCobertura: Boolean,
    val noControl: Boolean,
    val imageUri: String?
)