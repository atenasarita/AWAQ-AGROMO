package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.awaq_agromo.data.local.store.PlagueLocalStore
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlagueScreen(
    onNext: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    val savedPresence by PlagueLocalStore.readPresence(context).collectAsState(initial = "")
    val savedType by PlagueLocalStore.readPlagueType(context).collectAsState(initial = "")
    val savedParts by PlagueLocalStore.readAffectedParts(context).collectAsState(initial = "")
    val savedSeverity by PlagueLocalStore.readSeverity(context).collectAsState(initial = "")
    val savedDamage by PlagueLocalStore.readDamage(context).collectAsState(initial = "")
    val savedObservations by PlagueLocalStore.readObservations(context).collectAsState(initial = "")

    var plaguePresence by remember { mutableStateOf(savedPresence) }
    var selectedPlague by remember { mutableStateOf(savedType) }
    var affectedParts by remember { mutableStateOf(savedParts.split(", ").filter { it.isNotBlank() }.toSet()) }
    var severity by remember { mutableStateOf(savedSeverity) }
    var selectedDamage by remember { mutableStateOf(savedDamage.split(", ").filter { it.isNotBlank() }.toSet()) }
    var observations by remember { mutableStateOf(savedObservations) }

    var expandedSeverity by remember { mutableStateOf(false) }

    val severityOptions = listOf("Bajo", "Medio", "Alto", "Crítico")

    val plagueOptions = listOf(
        "Gusano cogollero" to R.drawable.gusano_cogollero,
        "Gusano de la mazorca" to R.drawable.gusano_mazorca,
        "Pulgón del maíz" to R.drawable.pulgon_maiz
    )

    val plantParts = listOf("Hoja", "Tallo", "Fruto", "Raíz")

    val damages = listOf(
        "Agujeros en hojas",
        "Hojas enrolladas",
        "Manchas",
        "Frutos perforados",
        "Presencia de insectos o larvas",
        "Otro"
    )

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
                HorizontalDotBar(n = 13, k = 7, modifier = Modifier.fillMaxWidth())
            }
            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Plagas",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textPrimary
                )
            }

            Spacer(Modifier.height(16.dp))

            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("¿Presencia de plagas?", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("Sí", "No").forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                plaguePresence = option
                                scope.launch { PlagueLocalStore.savePresence(context, option) }
                            }
                        ) {
                            RadioButton(
                                selected = plaguePresence == option,
                                onClick = {
                                    plaguePresence = option
                                    scope.launch { PlagueLocalStore.savePresence(context, option) }
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = accent)
                            )
                            Text(option)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Tipo de plaga", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    plagueOptions.forEach { (name, imgRes) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(100.dp)
                                .clickable {
                                    selectedPlague = name
                                    scope.launch { PlagueLocalStore.savePlagueType(context, name) }
                                }
                        ) {
                            Image(
                                painter = painterResource(id = imgRes),
                                contentDescription = name,
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = if (selectedPlague == name) 3.dp else 1.dp,
                                        color = if (selectedPlague == name) accent else borderSoft,
                                        shape = CircleShape
                                    ),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                name,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Parte/s afectada/s de la planta", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(8.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    plantParts.forEach { part ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Checkbox(
                                checked = affectedParts.contains(part),
                                onCheckedChange = { isChecked ->
                                    affectedParts = if (isChecked)
                                        affectedParts + part
                                    else
                                        affectedParts - part

                                    scope.launch {
                                        PlagueLocalStore.saveAffectedParts(
                                            context,
                                            affectedParts.joinToString(", ")
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
                Text("Nivel de severidad", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(6.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedSeverity,
                    onExpandedChange = { expandedSeverity = it }
                ) {
                    OutlinedTextField(
                        value = severity,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Seleccione nivel de severidad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSeverity) },
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
                        expanded = expandedSeverity,
                        onDismissRequest = { expandedSeverity = false }
                    ) {
                        severityOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    severity = opt
                                    expandedSeverity = false
                                    scope.launch { PlagueLocalStore.saveSeverity(context, opt) }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Tipo de daño observado",
                    color = textSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(8.dp))
                damages.forEach { dmg ->
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
                                    PlagueLocalStore.saveDamage(
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

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Observaciones", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = observations,
                    onValueChange = {
                        observations = it
                        scope.launch { PlagueLocalStore.saveObservations(context, it) }
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
                enabled = plaguePresence.isNotBlank(),
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
private fun PreviewPlagueScreen() {
    MaterialTheme { PlagueScreen() }
}