package com.example.awaq_agromo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.awaq_agromo.ui.theme.PrincipalPrimary

@Composable
fun AgromoHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "agromo",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = PrincipalPrimary // Usando el color principal
        )
        Text(
            text = "🌱",
            fontSize = 32.sp
        )
    }
}