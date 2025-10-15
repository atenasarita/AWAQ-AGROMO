package com.example.awaq_agromo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cultivo",
    foreignKeys = [
        ForeignKey(
            entity = AgricultorEntity::class,
            parentColumns = ["id_agricultor"],
            childColumns = ["id_agricultor"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class CultivoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_cultivo")
    val idCultivo: Long = 0,

    @ColumnInfo(name = "nombre")
    val nombre: String,

    @ColumnInfo(name = "fecha_siembra")
    val fechaSiembra: Long,

    @ColumnInfo(name = "area")
    val area: String,

    @ColumnInfo(name = "estado")
    val estado: String,

    @ColumnInfo(name = "estado_conexion")
    val estadoConexion: String,

    @ColumnInfo(name = "id_agricultor")
    val idAgricultor: Long? = null
)