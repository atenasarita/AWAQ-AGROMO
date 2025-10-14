package com.example.awaq_agromo.presentation.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.awaq_agromo.R
import com.example.awaq_agromo.components.BottomBar
import com.example.awaq_agromo.components.NavItem
import com.example.awaq_agromo.components.TitleText // Assuming you have this
import com.example.awaq_agromo.ui.theme.PrincipalPrimary // Your defined colors
import com.example.awaq_agromo.ui.theme.Primary300
import com.example.awaq_agromo.ui.theme.Primary900
import com.example.awaq_agromo.ui.theme.AgromoTheme // Your main theme

// --- Data Classes for easier data handling ---
data class CropItem(val iconRes: Int, val description: String)
data class InformeData(
    val date: String,
    val title: String,
    val status: String,
    val statusColor: Color,
    val imageRes: Int? = null // Optional image for report
)


val sampleCropItems = listOf(
    CropItem(R.drawable.chiili, "Chili"),
    CropItem(R.drawable.eggplant, "Berenjena"),
    CropItem(R.drawable.olive, "Aceituna"),
    CropItem(R.drawable.tomate, "Tomate"),
    CropItem(R.drawable.calabaza, "Calabaza"),
)

val sampleInformes: List<InformeData> = listOf(
    InformeData("12 sept", "Informe integral", "Pimiento", Color(0xFF6A9930), R.drawable.image_ph),
    InformeData("23 sept", "pH del suelo", "Café", Color(0xFFE6B800), R.drawable.planta_de_pimientos),
    InformeData("28 sept", "Fertilización", "Abono", Color(0xFF6A9930), R.drawable.image_ph),
    InformeData("05 oct", "Revisión hortalizas", "Atender", Color.Red, R.drawable.image_ph),
    InformeData("08 oct", "Detección de plaga", "Urgente", Color.Red, R.drawable.image_ph),
)

// --- Composable: DashboardScreen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    // State for selected bottom bar item
    var selectedRoute by remember { mutableStateOf(NavItem.Inicio.route) }

    val bottomBarHeight = 90.dp // Approximate height of your custom bottom bar

    Scaffold(
        // The top bar, if any, would go here
        // topBar = { /* Your top bar content */ },
        bottomBar = {
            BottomBar(
                selectedRoute = selectedRoute,
                onNavigate = { newRoute -> selectedRoute = newRoute }
            )
        }
    ) { paddingValues ->
        // Main scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply Scaffold's padding to avoid overlap with bottom bar
                .background(MaterialTheme.colorScheme.background), // Your screen background color
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp) // Spacing between sections
        ) {
            item { TopStatusBar() }
            item { HeaderSection("María Pia") }
            item { WeatherCard() }
            item { MonitoringCard() }
            item { MyCropsSection(sampleCropItems) }
            item { QuickInputSection() }
            item { CropPhotosSection() }
            item { RecentReportsSection(sampleInformes) }
        }
    }
}

// --- Individual UI Sections ---

@Composable
fun TopStatusBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Time
        Text("18:41", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        // Network/Battery Icons (placeholders)
        Row {
            Icon(Icons.Default.Wifi, contentDescription = null, Modifier.size(20.dp))
            Icon(Icons.Default.SignalCellularAlt, contentDescription = null, Modifier.size(20.dp))
            Icon(Icons.Default.BatteryFull, contentDescription = null, Modifier.size(20.dp))
        }
    }
}


@Composable
fun HeaderSection(userName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "¡Buenos días!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Options",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun WeatherCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Cuenca, Colombia",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "Ligeramente soleado",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "23°C",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(16.dp))
                // Weather icon placeholder
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = "Sunny",
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFFFFC107) // Yellow for sun
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherDetail("Humedad", "49%", Icons.Default.WaterDrop)
                WeatherDetail("Viento", "10 Km/h", Icons.Default.Air)
                WeatherDetail("Lluvias", "72%", Icons.Default.Cloud)
                WeatherDetailWithStatus("Pulverización", "Desfavorable", Color.Red, Icons.Default.Shower )
            }
        }
    }
}

@Composable
fun WeatherDetail(label: String, value: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label, Modifier.size(24.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall)
        Text(text = value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun WeatherDetailWithStatus(label: String, value: String, statusColor: Color, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label, Modifier.size(24.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall)
        Card(
            colors = CardDefaults.cardColors(containerColor = statusColor.copy(alpha = 0.2f)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = statusColor,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}


@Composable
fun MonitoringCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrincipalPrimary), // Dark green background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = "Monitoreo de Cultivo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary // White text
                )
                Text(
                    text = "Completa tus datos clave de tu cultivo paso a paso.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) // Slightly transparent white
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { /* TODO: Navigate to monitoring */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Comenzar", color = PrincipalPrimary, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Comenzar",
                        tint = PrincipalPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            // Image (man with hat)
            Image(
                painter = painterResource(id = R.drawable.tomafoto), // Replace with your image resource
                contentDescription = "Man with hat",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun MyCropsSection(crops: List<CropItem>) {
    Column {
        Text(
            text = "Mis cultivos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Acceda y gestione tus cultivos",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            crops.forEach { crop ->
                CropIcon(crop.iconRes, crop.description)
            }
        }
    }
}

@Composable
fun CropIcon(iconRes: Int, description: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant), // Light background for icons
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = description,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(text = description, style = MaterialTheme.typography.labelSmall)
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
                icon = Icons.Default.Eco, // Placeholder icon, adjust if you have a specific one
                onClick = { /* TODO */ }
            )
            QuickInputCard(
                label = "Plagas y\nenfermedades",
                icon = Icons.Default.BugReport, // Placeholder icon
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
fun QuickInputCard(label: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(100.dp) // Fixed width for the cards
            .height(120.dp), // Fixed height for the cards
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
                tint = PrincipalPrimary // Use your primary color for icons
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
fun CropPhotosSection() {
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
            onClick = { /* TODO: Take photo */ },
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

@Composable
fun RecentReportsSection(informes: List<InformeData>) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Resumen de informes\nrecientes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            TextButton(onClick = { /* TODO: View all reports */ }) {
                Text(
                    text = "Ver todos",
                    color = PrincipalPrimary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Ver todos",
                    tint = PrincipalPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        // Using LazyColumn here so it can scroll independently if needed,
        // but it's nested inside another LazyColumn for the main screen scroll.
        // It might be better to just use a Column if you don't expect many reports.
        // For simplicity and if `informes` isn't huge, let's just use Column.
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            informes.forEach { informe ->
                InformeCard(informe)
            }
        }
    }
}


@Composable
fun InformeCard(informe: InformeData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (informe.imageRes != null) {
                Image(
                    painter = painterResource(id = informe.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(12.dp))
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = informe.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = informe.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Dot indicator
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(informe.statusColor)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = informe.status,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(showSystemUi = true, backgroundColor = 0xFFF0F4C3) // Set a background color for preview
@Composable
fun DashboardScreenPreview() {
    AgromoTheme {
        DashboardScreen()
    }
}


