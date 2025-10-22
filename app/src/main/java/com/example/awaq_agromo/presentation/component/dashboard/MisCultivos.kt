package com.example.awaq_agromo.presentation.component.dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.presentation.theme.Primary900
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import com.example.awaq_agromo.presentation.theme.PrincipalSecondary

@Composable
fun MisCultivos(crops: List<String>) {

    Log.d("MisCultivos", "Crops list: $crops") // debug level log


    Column {
        Text(
            text = "Mis cultivos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Accede y gestiona tus cultivos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))

        if (crops.isEmpty()) {
            Text(
                text = "Empieza seleccionando tus cultivos para personalizar tu experiencia",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = PrincipalPrimary, // Material green 500
            )
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(crops) { cropName ->
                    CropNameChip(cropName)
                }
            }
        }
    }
}
@Composable
fun CropNameChip(name: String) {
    Box(
        modifier = Modifier
            .widthIn(min = 120.dp)       // slightly smaller width
            .heightIn(min = 40.dp)       // slightly smaller height
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)) // light green background
            .border(4.dp, PrincipalSecondary, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Primary900
        )
    }
}

