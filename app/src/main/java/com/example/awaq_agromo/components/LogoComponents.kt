package com.example.agromo_ai.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.example.awaq_agromo.R


@Composable
fun AgromoLogo(
) {
    Image(
        painter = painterResource(id = R.drawable.ic_agromo_logo), // Asegúrate de tener este recurso
        contentDescription = "Agromo Logo",
        modifier = Modifier.size(120.dp)
    )
}

@Composable
fun AgromoLogoDetailed(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(130.5.dp)
            .height(73.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .background(
                    color = Color(0xFF4CAF50),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )

        Text(
            text = "AG",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AgromoLogoWithText(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(130.5.dp)
            .height(73.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color(0xFF4CAF50),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🌱",
                fontSize = 20.sp
            )
        }

        Text(
            text = "agromo",
            color = Color(0xFF4CAF50),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}