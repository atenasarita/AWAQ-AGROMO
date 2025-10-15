package com.example.awaq_agromo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Long = 0,

    @ColumnInfo(name = "nombre_completo")
    val nombreCompleto: String,

    @ColumnInfo(name = "correo")
    val correo: String,

    @ColumnInfo(name = "password_hash")
    val passwordHash: String,

    @ColumnInfo(name = "telefono")
    val telefono: String,

    @ColumnInfo(name = "empresa")
    val empresa: String,

    @ColumnInfo(name = "cargo")
    val cargo: String,

    @ColumnInfo(name = "ubicacion")
    val ubicacion: String,

    @ColumnInfo(name = "rol")
    val rol: String,

    @ColumnInfo(name = "ultimo_acceso")
    val ultimoAcceso: String,

    @ColumnInfo(name = "estado_conexion")
    val estadoConexion: String
)