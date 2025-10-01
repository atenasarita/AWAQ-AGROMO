package com.example.awaq_agromo.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClimaScreen(navController: NavHostController) {
    var estadoClima by remember { mutableStateOf("") }
    var temperatura by remember { mutableStateOf(20f) }
    var humedad by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "2. Clima",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))


        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {}
        ) {
            OutlinedTextField(
                value = estadoClima,
                onValueChange = { estadoClima = it },
                label = { Text("Estado del Clima") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Temperatura del clima: ${temperatura.toInt()}°C")
        Slider(
            value = temperatura,
            onValueChange = { temperatura = it },
            valueRange = -10f..50f,
            steps = 6,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Humedad del aire: ${humedad.toInt()}%")
        Slider(
            value = humedad,
            onValueChange = { humedad = it },
            valueRange = 0f..100f,
            steps = 4,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Acción siguiente */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente")
        }
    }
}