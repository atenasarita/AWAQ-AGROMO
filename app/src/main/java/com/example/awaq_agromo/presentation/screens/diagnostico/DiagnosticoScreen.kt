package com.example.awaq_agromo.presentation.screens.diagnostico

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosticoScreen(navController: NavController) {
    val pestImages = remember { mutableStateListOf<Bitmap>() }
    val diseaseImages = remember { mutableStateListOf<Bitmap>() }
    val deficiencyImages = remember { mutableStateListOf<Bitmap>() }
    val weedImages = remember { mutableStateListOf<Bitmap>() }
    val nematodeImages = remember { mutableStateListOf<Bitmap>() }

    val pestLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        it?.let { pestImages.add(it) }
    }
    val diseaseLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        it?.let { diseaseImages.add(it) }
    }
    val deficiencyLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        it?.let { deficiencyImages.add(it) }
    }
    val weedLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        it?.let { weedImages.add(it) }
    }
    val nematodeLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        it?.let { nematodeImages.add(it) }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Diagnóstico de Campo") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SectionWithCamera(
                title = "Plagas observadas",
                images = pestImages,
                onAddPhoto = { pestLauncher.launch(null) }
            )
            SectionWithCamera(
                title = "Enfermedades (síntomas en hojas)",
                images = diseaseImages,
                onAddPhoto = { diseaseLauncher.launch(null) }
            )
            SectionWithCamera(
                title = "Deficiencias nutricionales",
                images = deficiencyImages,
                onAddPhoto = { deficiencyLauncher.launch(null) }
            )
            SectionWithCamera(
                title = "Malezas",
                images = weedImages,
                onAddPhoto = { weedLauncher.launch(null) }
            )
            SectionWithCamera(
                title = "Síntomas de nemátodos",
                images = nematodeImages,
                onAddPhoto = { nematodeLauncher.launch(null) }
            )
        }
    }
}

@Composable
fun SectionWithCamera(
    title: String,
    images: List<Bitmap>,
    onAddPhoto: () -> Unit
) {
    Column {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(images) { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp)
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onAddPhoto() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("+", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    }
}
