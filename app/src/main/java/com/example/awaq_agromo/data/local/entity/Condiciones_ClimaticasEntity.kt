package com.example.awaq_agromo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "condiciones_climaticas",
    foreignKeys = [
        ForeignKey(
            entity = FormularioEntity::class,
            parentColumns = ["id_formulario"],
            childColumns = ["id_formulario"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Condiciones_ClimaticasEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_condicion")
    val idCondicion: Long = 0,

    @ColumnInfo(name = "id_formulario")
    val idFormulario: Long = 0,

    @ColumnInfo(name = "estado_clima")
    val estadoClima: String,

    @ColumnInfo(name = "condiciones_tierra")
    val condicionesTierra: String,

    @ColumnInfo(name = "temperatura")
    val temperatura: String, // Real??

    @ColumnInfo(name = "humedad_ambiente")
    val humedadAmbiente: String, // Real??

    @ColumnInfo(name = "viento")
    val viento: String, // Real??

    @ColumnInfo(name = "humedad_tierra")
    val humedadTierra: String // Real??
)
