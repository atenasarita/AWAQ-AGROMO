package com.example.awaq_agromo.presentation.screens.manejo_aplicado

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FertilizacionScreen(navController: NavController) {
    var fertilizante by remember { mutableStateOf("") }
    var dosis by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            fecha = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro de Fertilización") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = fertilizante,
                onValueChange = { fertilizante = it },
                label = { Text("Fertilizante o insumo aplicado") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = dosis,
                onValueChange = { dosis = it },
                label = { Text("Dosis (kg/ha o L/ha)") },
                trailingIcon = {
                    Icon(Icons.Default.Scale, contentDescription = "Icono de balanza")
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fecha,
                onValueChange = {},
                label = { Text("Fecha de aplicación") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { datePicker.show() }) {
                        Icon(Icons.Default.CalendarToday, contentDescription = "Seleccionar fecha")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = observaciones,
                onValueChange = { observaciones = it },
                label = { Text("Observaciones (nota o texto libre)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Button(
                onClick = { /* TODO: Guardar datos o navegar atrás */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar registro")
            }
        }
    }
}
