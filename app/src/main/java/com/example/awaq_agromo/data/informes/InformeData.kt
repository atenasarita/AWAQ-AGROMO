package com.example.awaq_agromo.data.informes

import androidx.annotation.DrawableRes

data class InformeData(
    val date: String,
    val title: String,
    val status: String,
    val statusColor: androidx.compose.ui.graphics.Color,
    @DrawableRes val imagen: Int
)