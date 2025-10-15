package com.example.awaq_agromo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "fotografia",
    foreignKeys = [
        ForeignKey(
            entity = FormularioEntity::class,
            parentColumns = ["id_formulario"],
            childColumns = ["id_formulario"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class FotografiaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_foto")
    val idFoto: Int = 0,

    @ColumnInfo(name = "ruta_archivo")
    val rutaArchivo: String,

    @ColumnInfo(name = "fecha_foto")
    val fechaFoto: String,

    @ColumnInfo(name = "descripcion")
    val descripcion: String,

    @ColumnInfo(name = "estado_conexion")
    val estadoConexion: Boolean,

    @ColumnInfo(name = "id_formulario")
    val idFormulario: Int,

    @ColumnInfo(name = "archivo")
    val archivo: Long // Verificar el tipo de archivo que se le dara a la fotografia...
)
