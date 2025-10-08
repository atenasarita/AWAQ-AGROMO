package com.example.awaq_agromo.presentation.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun PerfilScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header con información del usuario
        UserHeader()

        // Lista de informes
        ReportsList()

        // Navigation Bottom Bar
        BottomNavigationBar()
    }
}

@Composable
fun UserHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar/Icono del usuario
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = Color(0xFF829500),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MP",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del usuario
        Text(
            text = "María Pardo",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ubicación
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Ubicación",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Cuenca, Ecuador",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ReportsList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        // Título de la sección
        Text(
            text = "Mis informes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Primer informe - Completo
        ReportItem(
            date = "12 sept",
            title = "Informe integral",
            status = "Completo",
            statusColor = Color(0xFF4CAF50) // Verde
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Segundo informe - Atender
        ReportItem(
            date = "23 sept",
            title = "pH del suelo",
            status = "Atender",
            statusColor = Color(0xFFFF9800) // Naranja
        )

        // Línea divisoria
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.4f)
        )
    }
}

@Composable
fun ReportItem(
    date: String,
    title: String,
    status: String,
    statusColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            // Fecha
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Título del informe
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Estado
        Box(
            modifier = Modifier
                .background(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = status,
                style = MaterialTheme.typography.labelMedium,
                color = statusColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val navigationItems = listOf(
        "Inicio" to Icons.Default.Home,
        "Monitoreo" to Icons.Default.Search,
        "Comunidad" to Icons.Default.Face,
        "Perfil" to Icons.Default.Person
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        navigationItems.forEach { (title, icon) ->
            BottomNavItem(
                title = title,
                icon = icon,
                isSelected = title == "Perfil" // Por ahora hardcodeado
            )
        }
    }
}

@Composable
fun BottomNavItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) Color(0xFF829500) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) Color(0xFF829500) else Color.Gray
        )
    }
}