package com.example.awaq_agromo.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val AgromoColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50),
    secondary = Color(0xFF66BB6A),
    tertiary = Color(0xFF81C784),
    background = Color(0xFFEFFFDE),
    surface = Color(0xFFEFFFDE), // Mismo color que background
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun AgromoTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = AgromoColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}