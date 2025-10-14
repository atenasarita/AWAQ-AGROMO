package com.example.awaq_agromo.presentation.component.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.example.awaq_agromo.R


@Composable
fun AgromoLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_agromo_logo), // Asegúrate de tener este recurso
        contentDescription = "Agromo Logo",
        modifier = modifier.size(120.dp)
    )
}