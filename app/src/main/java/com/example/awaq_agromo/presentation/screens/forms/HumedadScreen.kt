package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.awaq_agromo.R
import com.example.awaq_agromo.data.local.store.HumedadLocalStore
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import com.example.awaq_agromo.presentation.viewmodel.HumedadViewModel
import kotlinx.coroutines.launch
import kotlin.collections.forEach
import kotlin.let
import kotlin.text.all
import kotlin.text.isDigit
import kotlin.text.isEmpty
import kotlin.text.isNotBlank
import kotlin.text.take
import kotlin.text.toIntOrNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumedadScreen(
    onNext: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Colores de la app
    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    var selectedDesc by remember { mutableStateOf("Manual") } // description: manual or sensor
    var selectedValue by remember { mutableStateOf("") }
    val valueInt = selectedValue.toIntOrNull() ?: 0

    // Persistencia local (DataStore)
    val savedManual by HumedadLocalStore.readManualScale(context).collectAsState(initial = null)
    val savedSensor by HumedadLocalStore.readSensorValue(context).collectAsState(initial = null)

    var manualExpanded by remember { mutableStateOf(false) }
    var manualScale by remember { mutableStateOf(savedManual ?: "") }
    var sensorValue by remember { mutableStateOf(savedSensor ?: "") }
    var sensorError by remember { mutableStateOf<String?>(null) }

    // Opciones de escala
    val scaleOptions = listOf("Muy seco", "Seco", "Medio", "Húmedo", "Muy húmedo")

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(bgScreen),
        color = bgScreen
    ) {
        Column(Modifier.fillMaxSize()) {

            // Encabezado y progreso
            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Suelo y condiciones",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = textPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Complete los datos que disponga; el resto puede omitirlo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary
                )
                Spacer(Modifier.height(10.dp))
                HorizontalDotBar(n = 11, k = 3, modifier = Modifier.fillMaxWidth())
            }

            // Sección principal
            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Humedad del suelo",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textPrimary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Ingresar la humedad en la escala si es manualmente o anote el valor si posee un sensor.",
                    style = MaterialTheme.typography.bodySmall,
                    color = textSecondary
                )
            }

            // Imagen decorativa
            Spacer(Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.suelo), // coloca suelo.png en res/drawable
                contentDescription = "Humedad del suelo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 36.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            // Registro manual (dropdown)
            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Registro manual", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(6.dp))
                ExposedDropdownMenuBox(
                    expanded = manualExpanded,
                    onExpandedChange = { manualExpanded = it }
                ) {
                    OutlinedTextField(
                        value = manualScale,
                        onValueChange = { /* readOnly */ },
                        readOnly = true,
                        placeholder = { Text("Indique humedad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = manualExpanded) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accent,
                            unfocusedBorderColor = borderSoft,
                            cursorColor = accent
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = manualExpanded,
                        onDismissRequest = { manualExpanded = false }
                    ) {
                        scaleOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    manualScale = opt
                                    manualExpanded = false
                                    scope.launch { HumedadLocalStore.saveManualScale(context, opt) }
                                }
                            )
                        }
                    }
                }
            }

            // Registro con sensor
            Spacer(Modifier.height(12.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Registro con sensor digital", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = sensorValue,
                    onValueChange = { v ->
                        // Solo números y rango 0..100
                        if (v.isEmpty() || v.all { it.isDigit() }) {
                            val trimmed = v.take(3)
                            sensorValue = trimmed
                            sensorError = trimmed.toIntOrNull()?.let { if (it in 0..100) null else "Debe estar entre 0 y 100" }
                            if (sensorError == null) {
                                scope.launch { HumedadLocalStore.saveSensorValue(context, trimmed) }
                            }
                        }
                    },
                    isError = sensorError != null,
                    supportingText = {
                        if (sensorError != null) Text(sensorError!!, color = Color(0xFF8B0000))
                    },
                    placeholder = { Text("Valor exacto en %") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accent,
                        unfocusedBorderColor = borderSoft,
                        cursorColor = accent
                    ),
                    suffix = { Text("%") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // CTA
            Spacer(Modifier.height(18.dp))
            Button(
                onClick = onNext ,
                enabled = manualScale.isNotBlank() || (sensorValue.toIntOrNull()?.let { it in 0..100 } == true),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accent,
                    disabledContainerColor = accent.copy(alpha = 0.4f)
                )
            ) { Text("Siguiente") }

            Spacer(Modifier.height(12.dp))
        }
    }
}

/* ---------------- Preview ---------------- */