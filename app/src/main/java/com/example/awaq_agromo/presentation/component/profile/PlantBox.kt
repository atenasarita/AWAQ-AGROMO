package com.example.awaq_agromo.presentation.component.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.R
import androidx.compose.ui.text.font.FontWeight

@Composable
fun PlantBox(
    planta: String = "Defina el tipo de planta",
    color: Color = Color.White,
    borderColor: Color = Color(0xFF344E18),
    textColor: Color = Color(0xFF344E18),
    iconRes: Int = R.drawable.chiili
) {
    Box(
        modifier = Modifier
            .background(color = color)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Planta Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = planta,
                style = MaterialTheme.typography.labelMedium,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantBoxPreview() {
    PlantBox(planta = "Café")
}
