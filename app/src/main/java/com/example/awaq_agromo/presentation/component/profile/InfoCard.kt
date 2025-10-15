package com.example.awaq_agromo.presentation.component.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.R
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
import androidx.compose.material3.MaterialTheme
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
import com.example.awaq_agromo.presentation.component.texts.SubtitleText
import com.example.awaq_agromo.presentation.screens.dashboard.InformeData


@Composable
fun InfoCard(
    informe: InformeData,
    onMoreInformationClick: () -> Unit = {}
) {
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
                color = Color(0xFF344E18),
                shape = RoundedCornerShape(cornerRadius)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (informe.imageRes != null) {
            Image(
                painter = painterResource(id = informe.imageRes),
                contentDescription = informe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(cornerRadius))
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = informe.date,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(end = 10.dp)
                )
                PlantBox(planta = informe.planta) // you can show the "plant/status" here
            }
            SubtitleText(text = informe.title)
            StatusBox(status = informe.status, statusColor = informe.statusColor)
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onMoreInformationClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "More information",
                tint = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoCardPreview() {
    val sampleInforme = InformeData(
        date = "12 sept",
        title = "Informe integral",
        status = "Completo",
        statusColor = Color(0xFF6A9930),
        planta = "Cebolla"
    )

    InfoCard(
        informe = sampleInforme,
        onMoreInformationClick = { /* For preview, no action */ }
    )
}