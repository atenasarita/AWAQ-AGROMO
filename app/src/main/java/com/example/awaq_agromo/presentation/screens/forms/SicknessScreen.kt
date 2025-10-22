package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.R
import com.example.awaq_agromo.data.local.store.SicknessLocalStore
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SicknessScreen(
    onNext: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    val savedPresence by SicknessLocalStore.readPresence(context).collectAsState(initial = "")
    val savedDamage by SicknessLocalStore.readDamage(context).collectAsState(initial = "")
    val savedParts by SicknessLocalStore.readParts(context).collectAsState(initial = "")
    val savedAffectLevel by SicknessLocalStore.readAffectLevel(context).collectAsState(initial = "Todo bien")
    val savedObservations by SicknessLocalStore.readObservations(context).collectAsState(initial = "")

    var presence by remember { mutableStateOf(savedPresence) }
    var selectedDamage by remember { mutableStateOf(savedDamage.split(", ").filter { it.isNotBlank() }.toSet()) }
    var selectedParts by remember { mutableStateOf(savedParts.split(", ").filter { it.isNotBlank() }.toSet()) }
    var affectLevel by remember { mutableStateOf(savedAffectLevel) }
    var observations by remember { mutableStateOf(savedObservations) }

    var sliderValue by remember { mutableStateOf(0f) }

    val damageOptions = listOf(
        "Manchas amarillas o café",
        "Polvo blanco o gris (hongos)",
        "Hojas marchitas o secas",
        "Tallos podridos o con lesiones",
        "Frutos deformados o con pudrición",
        "Ninguno"
    )

    val plantParts = listOf("Hojas", "Tallo", "Raíz", "Fruto", "Toda la planta", "Ninguna")

    val affectLabels = listOf("Todo bien", "Leve", "Media", "Alta")

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(bgScreen),
        color = bgScreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Spacer(Modifier.height(16.dp))

            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Problemas y riesgos",
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
                HorizontalDotBar(n = 13, k = 8, modifier = Modifier.fillMaxWidth())
            }
            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Enfermedades",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textPrimary
                )
            }

            Spacer(Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.plant_sickness),
                contentDescription = "Síntomas de enfermedades",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 36.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("¿Observas síntomas de enfermedad en tu cultivo?", color = textSecondary)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    listOf("Sí", "No").forEach { opt ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                presence = opt
                                scope.launch { SicknessLocalStore.savePresence(context, opt) }
                            }
                        ) {
                            RadioButton(
                                selected = presence == opt,
                                onClick = {
                                    presence = opt
                                    scope.launch { SicknessLocalStore.savePresence(context, opt) }
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = accent)
                            )
                            Text(opt)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            // --- Tipo de daño observado ---
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Tipo de daño observado", color = textSecondary)
                Spacer(Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    damageOptions.forEach { dmg ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.width(180.dp)
                        ) {
                            Checkbox(
                                checked = selectedDamage.contains(dmg),
                                onCheckedChange = { isChecked ->
                                    selectedDamage = if (isChecked)
                                        selectedDamage + dmg
                                    else
                                        selectedDamage - dmg

                                    scope.launch {
                                        SicknessLocalStore.saveDamage(
                                            context,
                                            selectedDamage.joinToString(", ")
                                        )
                                    }
                                },
                                colors = CheckboxDefaults.colors(checkedColor = accent)
                            )
                            Text(
                                text = dmg,
                                color = textPrimary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("¿Cuál o cuáles son las partes afectadas de la planta?", color = textSecondary)
                Spacer(Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    plantParts.forEach { part ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.width(180.dp)
                        ) {
                            Checkbox(
                                checked = selectedParts.contains(part),
                                onCheckedChange = { isChecked ->
                                    selectedParts = if (isChecked)
                                        selectedParts + part
                                    else
                                        selectedParts - part

                                    scope.launch {
                                        SicknessLocalStore.saveParts(
                                            context,
                                            selectedParts.joinToString(", ")
                                        )
                                    }
                                },
                                colors = CheckboxDefaults.colors(checkedColor = accent)
                            )

                            Text(
                                text = part,
                                color = textPrimary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }


            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Nivel de afectación del cultivo", color = textSecondary)
                Spacer(Modifier.height(6.dp))
                Slider(
                    value = sliderValue,
                    onValueChange = { value ->
                        sliderValue = value
                        val index = (value * (affectLabels.size - 1)).toInt().coerceIn(0, affectLabels.lastIndex)
                        affectLevel = affectLabels[index]
                        scope.launch { SicknessLocalStore.saveAffectLevel(context, affectLevel) }
                    },
                    steps = 2,
                    colors = SliderDefaults.colors(
                        thumbColor = accent,
                        activeTrackColor = accent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    affectLabels.forEach { label ->
                        Text(label, color = textSecondary, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Observaciones", color = textSecondary)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = observations,
                    onValueChange = {
                        observations = it
                        scope.launch { SicknessLocalStore.saveObservations(context, it) }
                    },
                    placeholder = { Text("Notas") },
                    minLines = 3,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accent,
                        unfocusedBorderColor = borderSoft,
                        cursorColor = accent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp)
                )
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { onNext?.invoke() },
                enabled = presence.isNotBlank(),
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

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF4F8EF)
@Composable
private fun PreviewSicknessScreen() {
    MaterialTheme { SicknessScreen() }
}
