package com.example.awaq_agromo.presentation.component.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.ranges.coerceAtLeast
import kotlin.ranges.coerceIn


@Composable
fun HorizontalDotBar(
    n: Int,
    k: Int,
    modifier: Modifier = Modifier,
    dotDiameter: Dp = 10.dp,
    spacing: Dp = 14.dp,
    activeColor: Color = Color(0xFF2E4A1F),   // Verde oscuro similar al ejemplo
    inactiveColor: Color = Color(0xFFCBD5C0), // Gris claro
    inactiveAlpha: Float = 0.35f
) {
    val total = n.coerceAtLeast(1)
    val active = k.coerceIn(0, total)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        repeat(total) { index ->
            val isActive = index < active
            val targetColor =
                if (isActive) activeColor else inactiveColor.copy(alpha = inactiveAlpha)
            val color by animateColorAsState(targetColor, label = "dotColor")

            Canvas(modifier = Modifier.size(dotDiameter)) {
                val radius = size.minDimension / 2f
                drawCircle(color = color, radius = radius, center = Offset(radius, radius))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalDotBarPreview() {
    HorizontalDotBar(
        // esto es lo que se modifica
        n = 13,
        k = 1,
        modifier = Modifier.padding(16.dp)
    )
}
