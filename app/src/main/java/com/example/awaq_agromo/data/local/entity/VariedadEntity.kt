package com.example.awaq_agromo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "variedad_form")
data class VariedadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val crops: String,      // Stored as a comma-separated string
    val sowingDate: String? // Optional, since the user may skip it
)
