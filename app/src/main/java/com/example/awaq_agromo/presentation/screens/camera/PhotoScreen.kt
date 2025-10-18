package com.example.awaq_agromo.presentation.screens.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PhotoScreen(navController: NavController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Crear el archivo para guardar la foto
    val photoFile = remember {
        createImageFile(context)
    }

    val photoUri = remember(photoFile) {
        photoFile?.let { file ->
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        }
    }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        isLoading = false

        if (success && photoUri != null && photoFile?.exists() == true) {
            // Foto capturada exitosamente
            try {
                // Codificar el URI como string para pasarlo de forma segura
                val uriString = photoUri.toString()
                val encodedUri = Uri.encode(uriString)
                navController.navigate("analysis/$encodedUri") {
                    popUpTo("photo_screen") { inclusive = true }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Error al navegar: ${e.message}"
                photoFile?.delete()
            }
        } else {
            // Usuario canceló o hubo error
            photoFile?.delete() // Limpiar archivo si existe
            navController.popBackStack()
        }
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted && photoUri != null) {
            try {
                cameraLauncher.launch(photoUri)
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Error al abrir la cámara: ${e.message}"
            }
        } else {
            isLoading = false
            errorMessage = "Permiso de cámara denegado"
            // Volver después de mostrar el error brevemente
            navController.popBackStack()
        }
    }

    // Lanzar cámara automáticamente al entrar a la pantalla
    LaunchedEffect(Unit) {
        if (photoFile == null || photoUri == null) {
            isLoading = false
            errorMessage = "Error al crear archivo temporal"
            delay(1500)
            navController.popBackStack()
            return@LaunchedEffect
        }

        delay(300) // Pequeño delay para transición suave

        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            try {
                cameraLauncher.launch(photoUri)
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Error al abrir la cámara: ${e.message}"
            }
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Limpiar archivo si el usuario sale sin tomar foto
    DisposableEffect(Unit) {
        onDispose {
            if (photoFile?.exists() == true) {
                // Solo eliminar si no navegamos a análisis
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                if (currentRoute != "analysis/{photoUri}") {
                    photoFile.delete()
                }
            }
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading && errorMessage == null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Abriendo cámara...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            errorMessage != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage!!,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.popBackStack() }
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}

// Función auxiliar para crear el archivo de imagen
private fun createImageFile(context: Context): File? {
    return try {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}