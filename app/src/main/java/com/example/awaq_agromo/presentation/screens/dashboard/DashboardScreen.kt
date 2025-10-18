package com.example.awaq_agromo.presentation.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.awaq_agromo.R
import com.example.awaq_agromo.presentation.component.ui.HeaderSection
import com.example.awaq_agromo.presentation.component.ui.NavItem
import com.example.awaq_agromo.presentation.component.dashboard.MonitoreoCard
import com.example.awaq_agromo.presentation.component.dashboard.MisCultivos
import com.example.awaq_agromo.presentation.component.dashboard.WeatherCard
import com.example.awaq_agromo.presentation.component.profile.InfoCard
import com.example.awaq_agromo.presentation.model.InformeData
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import com.example.awaq_agromo.presentation.theme.AgromoTheme

data class CropItem(val iconRes: Int, val description: String)

val sampleCropItems = listOf(
    CropItem(R.drawable.chiili, "Chili"),
    CropItem(R.drawable.eggplant, "Berenjena"),
    CropItem(R.drawable.olive, "Aceituna"),
    CropItem(R.drawable.tomate, "Tomate"),
    CropItem(R.drawable.calabaza, "Calabaza"),
    CropItem(R.drawable.olive, "Aceituna"),
    CropItem(R.drawable.olive, "Aceituna"),
)

val sampleInformesRecientes: List<InformeData> = listOf(
    InformeData("12 sept", "Informe integral", "Pimiento", Color(0xFF6A9930), R.drawable.image_ph, "Calabaza"),
    InformeData("23 sept", "pH del suelo", "Café", Color(0xFFE6B800), R.drawable.planta_de_pimientos, "Pimiento"),
    InformeData("28 sept", "Fertilización", "Abono", Color(0xFF6A9930), R.drawable.image_ph, "Aceituna"),
    InformeData("05 oct", "Revisión hortalizas", "Atender", Color.Red, R.drawable.image_ph, "Tomate"),
    InformeData("08 oct", "Detección de plaga", "Urgente", Color.Red, R.drawable.image_ph, "Tomate"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController,onPhotoClick: () -> Unit = {}) {

    Scaffold(
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(35.dp)
        ) {
            item { HeaderSection("María Pia") }
            item { WeatherCard() }
            item { MonitoreoCard( navController = navController) }

            item { MisCultivos(sampleCropItems) }
            item { QuickInputSection() }
            item { CropPhotosSection(onPhotoClick) }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Informes Recientes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(
                        onClick = {
                            navController.navigate(NavItem.Perfil.route) {
                                popUpTo(NavItem.Inicio.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Text(
                            text = "Ver todo",
                            color = PrincipalPrimary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            items(sampleInformesRecientes) { informe ->
                InfoCard(
                    informe = informe,
                    onMoreInformationClick = {
                        // Optional: navigate to detailed report
                    }
                )
            }
        }
    }
}



@Composable
fun QuickInputSection() {
    Column {
        Text(
            text = "Ingreso rápido",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Registra diagnósticos de plagas, malezas y otros datos en el momento.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            QuickInputCard(
                label = "Añada nuevo\ncultivo",
                icon = Icons.Default.Add,
                onClick = { /* TODO */ }
            )
            QuickInputCard(
                label = "Estado\nfenológico",
                icon = Icons.Default.Eco, // placeholders
                onClick = { /* TODO */ }
            )
            QuickInputCard(
                label = "Plagas y\nenfermedades",
                icon = Icons.Default.BugReport, // placeholders
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
fun QuickInputCard(label: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(36.dp),
                tint = PrincipalPrimary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun CropPhotosSection(onPhotoClick: () -> Unit = {}) {
    Column {
        Text(
            text = "Fotografía de tus cultivos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Toma tus fotos y súmala al informe de tu cultivo.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            PhotoInstructionCard("1", "Tomar foto de cultivo", R.drawable.tomafoto)
            PhotoInstructionCard("2", "Añádala a su informe", R.drawable.informenuevo)
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onPhotoClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrincipalPrimary),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text("Tomar foto", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun PhotoInstructionCard(number: String, instruction: String, imageRes: Int) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Green.copy(alpha = 0.2f)), // Light green circle
                contentAlignment = Alignment.Center
            ) {
                Text(text = number, color = Color.Green, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = instruction,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    AgromoTheme {
        val navController = rememberNavController()
        DashboardScreen(navController = navController)
    }
}