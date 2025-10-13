package com.example.awaq_agromo.components.profileComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.R

@Composable
fun PlantBox(
    color: Color = Color.White,
    borderColor: Color = Color(0xFF344E18),
    textColor: Color = Color(0xFF344E18),
    planta: String = "Defina el tipo de planta"
) {
    Box(
        modifier = Modifier
            .background(
                color = color
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.chiili),
                contentDescription = "Planta de Pimientos",
                modifier = Modifier.padding(start = 8.dp,end = 8.dp).size(20.dp)
            )
            Text(
                text = planta,
                style = MaterialTheme.typography.labelMedium,
                color = textColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantBoxPreview() {
    InfoCard()
}

