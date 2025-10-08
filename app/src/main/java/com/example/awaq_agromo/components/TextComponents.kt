package com.example.awaq_agromo.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = Color(0xFF4CAF50)
        ),
        modifier = modifier
    )
}

@Composable
fun SubtitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF666666)
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 18.sp,
            color = color,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        ),
        modifier = modifier
    )
}


@Composable
fun BodyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF666666)
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 16.sp,
            color = color
        ),
        modifier = modifier
    )
}