package com.example.awaq_agromo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalles_quimicos",
    foreignKeys = [
        ForeignKey(
            entity = FormularioEntity::class,
            parentColumns = ["id_formulario"],
            childColumns = ["id_formulario"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Detaller_QuimicosEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_detalle")
    val idDetalle : Int = 0,

    @ColumnInfo(name = "id_formulario")
    val idFormulario : Int,

    @ColumnInfo(name = "tipo_quimico")
    val tipoQuimico: String,

    @ColumnInfo(name = "metodo_aplicacion")
    val medotoAplicacion: String
)
