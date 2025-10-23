package com.example.awaq_agromo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "form_data")
data class FormEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int?,
    val selectedCrops: String?, // comma-separated
    val sowingDate: String? = null,
    val humedadDesc: String?,
    val humedadValue: Int?
)
