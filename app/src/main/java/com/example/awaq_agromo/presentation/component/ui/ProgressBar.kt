package com.example.awaq_agromo.presentation.component.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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