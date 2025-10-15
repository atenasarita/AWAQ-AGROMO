package com.example.awaq_agromo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "chat_ia",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id_usuario"],
            childColumns = ["id_usuario"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Chat_aiEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_chat")
    val idChat: Int = 0,

    @ColumnInfo(name = "id_usuario")
    val idUsuario: String,

    @ColumnInfo(name = "mensaje")
    val mensaje: String,

    @ColumnInfo(name = "imagen:")
    val imagen: Long, // Aqui tambien hay una referencia de imagen...

    @ColumnInfo(name = "creado_en")
    val creadoEn: String
)
