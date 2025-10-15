package com.example.awaq_agromo.presentation.component.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import java.nio.file.Files.size

@Composable
fun ProgressBar(progress: Float) {
    LinearProgressIndicator(
    progress = { progress },
    modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
    color = Color(0xFF829500),
    trackColor = Color.LightGray.copy(alpha = 0.4f),
    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
    )
}

@Composable
fun CustomProgressBar(progress: Float) {
    val trackColor = Color.LightGray.copy(alpha = 0.4f)
    val progressColor = Color(0xFF829500)
    val height = 8.dp
    val strokeCap = StrokeCap.Round

    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val cornerRadius = 4.dp.toPx()

        drawRoundRect(
            color = trackColor,
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )

        // Dibujar la primera mitad siempre blanca
        val halfWidth = canvasWidth / 2
        drawRoundRect(
            color = Color.Transparent,
            size = Size(halfWidth, canvasHeight),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )

        // Dibujar el progreso solo en la segunda mitad
        if (progress > 0.5f) {
            val progressInSecondHalf = (progress - 0.5f) * 2f // Convertir a 0-1 para la segunda mitad
            val progressWidth = halfWidth * progressInSecondHalf.coerceIn(0f, 1f)

            drawRoundRect(
                color = progressColor,
                topLeft = Offset(halfWidth, 0f),
                size = Size(progressWidth, canvasHeight),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }
    }
}