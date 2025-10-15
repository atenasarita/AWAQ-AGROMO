package com.example.awaq_agromo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "agricultor",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id_usuario"],
            childColumns = ["id_usuario"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class AgricultorEntity(
    @PrimaryKey(autoGenerate = true)
    val idAgricultor: Long = 0,

    @ColumnInfo(name = "nombre")
    val nombre: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "ubicacion")
    val ubicacion: String,

    @ColumnInfo(name = "estado_conexion")
    val estadoConexion: String,

    @ColumnInfo(name = "foto_perfil")
    val fotoPerfil: ByteArray? = null,

    @ColumnInfo(name = "id_usuario")
    val idUsuario: Long? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AgricultorEntity

        if (idAgricultor != other.idAgricultor) return false
        if (idUsuario != other.idUsuario) return false
        if (nombre != other.nombre) return false
        if (email != other.email) return false
        if (ubicacion != other.ubicacion) return false
        if (estadoConexion != other.estadoConexion) return false
        if (fotoPerfil != null) {
            if (other.fotoPerfil == null) return false
            if (!fotoPerfil.contentEquals(other.fotoPerfil)) return false
        } else if (other.fotoPerfil != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = idAgricultor.hashCode()
        result = 31 * result + (idUsuario?.hashCode() ?: 0)
        result = 31 * result + nombre.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + ubicacion.hashCode()
        result = 31 * result + estadoConexion.hashCode()
        result = 31 * result + (fotoPerfil?.contentHashCode() ?: 0)
        return result
    }
}