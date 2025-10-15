package com.example.awaq_agromo.presentation.screens.finalizacion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroExitosoScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro Completado") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro completado con éxito",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("diagnostico") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Nuevo registro")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { navController.navigate("reportes") }, // se cambia cuando hagamos que los reportes se guarden
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver reportes")
            }
        }
    }
}
