package com.example.awaq_agromo.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IdCultivoScreen() {
    var tipoCultivo by remember { mutableStateOf("") }
    var fechaSiembra by remember { mutableStateOf("") }
    var variedad by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "1. Identificación del Cultivo", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tipoCultivo,
            onValueChange = { tipoCultivo = it },
            label = { Text("Tipo de cultivo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fechaSiembra,
            onValueChange = { fechaSiembra = it },
            label = { Text("Fecha de siembra (dd/mm/yyyy)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = variedad,
            onValueChange = { variedad = it },
            label = { Text("Variedad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = region,
            onValueChange = { region = it },
            label = { Text("Región de siembra") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Aquí irá la acción futura */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente")
        }
    }
}