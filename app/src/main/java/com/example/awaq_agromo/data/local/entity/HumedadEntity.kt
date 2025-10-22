package com.example.awaq_agromo.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "humedad")
data class HumedadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val humedadDesc: String,
    val humedadValue: Int
)