package com.example.awaq_agromo.presentation.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class InformeData(
    val date: String,
    val title: String,
    val status: String,
    val statusColor: Color,
    @DrawableRes val imagen: Int
)