package com.example.awaq_agromo.components.profileComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.R
import com.example.awaq_agromo.components.SubtitleText

@Composable
fun InfoCard(
    date: String = "12 sept",
    status: String = "Atender",
    title: String = "Informe integral",
    statusColor: Color = Color.LightGray,
    borderColor: Color = Color(0xFF344E18),
    imagen: Int = R.drawable.planta_de_pimientos,
    onMoreInformationClick: () -> Unit = {}
){
    val cornerRadius = 16.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(cornerRadius)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(cornerRadius)
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imagen),
            contentDescription = "Planta de Pimientos",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(shape = RoundedCornerShape(cornerRadius))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = date,
                    modifier = Modifier.padding(end = 10.dp))

                PlantBox(
                    planta = "Cafe",
                    borderColor = borderColor
                )
            }
            SubtitleText(text = title)

            StatusBox(status = status, statusColor = statusColor)
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = onMoreInformationClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "More information button",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun InfoCardPreview() {
    InfoCard()
}