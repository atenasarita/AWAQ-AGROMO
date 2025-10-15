package com.example.awaq_agromo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "formulario",
    foreignKeys = [
        ForeignKey(
            entity = AgricultorEntity::class,
            parentColumns = ["id_agricultor"],
            childColumns = ["id_agricultor"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = CultivoEntity::class,
            parentColumns = ["id_cultivo"],
            childColumns = ["id_cultivo"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class FormularioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_formulario")
    val idFormulario: Long = 0,

    @ColumnInfo(name = "nombre_formulario")
    val nombreFormulario: String,

    @ColumnInfo(name = "fecha")
    val fecha: Long,

    @ColumnInfo(name = "hora")
    val hora: String,

    @ColumnInfo(name = "estado")
    val estado: String,

    @ColumnInfo(name = "nombre_operador")
    val nombreOperado: String,

    @ColumnInfo(name = "medidas_plantio")
    val medidasPlantio: String,

    @ColumnInfo(name = "datos_clima")
    val datosClima: String,

    @ColumnInfo(name = "observaciones")
    val observaciones: String,

    @ColumnInfo(name = "estado_conexion")
    val estadoConexion: String,

    @ColumnInfo(name = "id_agricultor")
    val idAgricultor: Long? = null,

    @ColumnInfo(name = "id_usuario")
    val idUsuario: Long
)
