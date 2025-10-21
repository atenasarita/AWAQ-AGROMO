package com.example.awaq_agromo.presentation.component.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckRow(
    label: String,
    checked: Boolean,
    onToggle: () -> Unit,
    borderColor: Color = Color(0xFFBBD8A8),
    textColor: Color = Color(0xFF1D1D1D),
    backgroundColor: Color = Color.White
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onToggle() }
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = textColor,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = { onToggle() }
        )
    }
}

/* -------------------- Preview -------------------- */
@Preview(showBackground = true)
@Composable
private fun CheckRowPreview() {
    Column(Modifier.padding(16.dp)) {
        CheckRow(
            label = "Opcion 1: Maiz",
            checked = true,
            onToggle = {}
        )
        Spacer(Modifier.height(8.dp))
        CheckRow(
            label = "Opcion 2: Cafe",
            checked = false,
            onToggle = {}
        )
    }
}