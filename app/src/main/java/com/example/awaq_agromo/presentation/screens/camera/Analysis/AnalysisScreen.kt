package com.example.awaq_agromo.presentation.screens.camera.Analysis

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.io.FileNotFoundException
import androidx.core.net.toUri

@Composable
fun AnalysisScreen(navController: NavController, imageUri: String?) {
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Convertir string a Uri de forma segura
    val uri = remember(imageUri) {
        imageUri?.toUri()
    }

    // Verificar que la Uri es válida
    val isValidImage = remember(uri) {
        if (uri == null) {
            errorMessage = "URI de imagen es nula"
            false
        } else {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val exists = inputStream != null
                inputStream?.close()
                if (!exists) errorMessage = "El archivo de imagen no existe"
                exists
            } catch (e: SecurityException) {
                errorMessage = "Error de permisos: ${e.message}"
                false
            } catch (e: FileNotFoundException) {
                errorMessage = "Archivo no encontrado: ${e.message}"
                false
            } catch (e: Exception) {
                errorMessage = "Error al cargar imagen: ${e.message}"
                false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorMessage != null) {
            Text(
                "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            Button(
                onClick = { navController.popBackStack() }
            ) {
                Text("Volver")
            }
        } else if (isValidImage && uri != null) {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Imagen para análisis",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(24.dp))

            Text(
                "Análisis de la imagen...",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    // Aquí tu lógica de análisis
                }
            ) {
                Text("Analizar imagen")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() }
            ) {
                Text("Volver a tomar foto")
            }

            Button(
                onClick = {
                    navController.navigate("photo_screen") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Text("Tomar otra foto")
            }
        } else {
            CircularProgressIndicator()
            Text("Cargando imagen...", modifier = Modifier.padding(top = 16.dp))
        }
    }
}