package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.R
import com.example.awaq_agromo.data.local.store.ConditionsLocalStore
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import kotlinx.coroutines.launch
import kotlin.collections.forEach
import kotlin.text.all
import kotlin.text.isDigit
import kotlin.text.isNotBlank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConditionsScreen(
    onNext: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    val savedLevel by ConditionsLocalStore.readFertilityLevel(context).collectAsState(initial = null)
    val savedN by ConditionsLocalStore.readNitrogen(context).collectAsState(initial = null)
    val savedP by ConditionsLocalStore.readPhosphorus(context).collectAsState(initial = null)
    val savedK by ConditionsLocalStore.readPotassium(context).collectAsState(initial = null)
    val savedMO by ConditionsLocalStore.readOrganicMatter(context).collectAsState(initial = null)
    val savedCIC by ConditionsLocalStore.readCIC(context).collectAsState(initial = null)

    var fertilityExpanded by remember { mutableStateOf(false) }
    var fertilityLevel by remember { mutableStateOf(savedLevel ?: "") }

    var nitrogen by remember { mutableStateOf(savedN ?: "") }
    var phosphorus by remember { mutableStateOf(savedP ?: "") }
    var potassium by remember { mutableStateOf(savedK ?: "") }
    var organicMatter by remember { mutableStateOf(savedMO ?: "") }
    var cic by remember { mutableStateOf(savedCIC ?: "") }

    val options = listOf("Bajo", "Medio", "Alto")

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(bgScreen),
        color = bgScreen
    ) {
        Column(Modifier.fillMaxSize()) {
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
                HorizontalDotBar(n = 13, k = 3, modifier = Modifier.fillMaxWidth())
            }

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Fertilidad del suelo",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textPrimary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Indique la fertilidad del suelo, ya sea mediante análisis digital o de forma manual.",
                    style = MaterialTheme.typography.bodySmall,
                    color = textSecondary
                )
            }

            Spacer(Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.soil_illustration),
                contentDescription = "Ilustración suelo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 36.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Registro manual", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(6.dp))
                ExposedDropdownMenuBox(
                    expanded = fertilityExpanded,
                    onExpandedChange = { fertilityExpanded = it }
                ) {
                    OutlinedTextField(
                        value = fertilityLevel,
                        onValueChange = { },
                        readOnly = true,
                        placeholder = { Text("Nivel de fertilidad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fertilityExpanded) },
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
                        expanded = fertilityExpanded,
                        onDismissRequest = { fertilityExpanded = false }
                    ) {
                        options.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    fertilityLevel = opt
                                    fertilityExpanded = false
                                    scope.launch { ConditionsLocalStore.saveFertilityLevel(context, opt) }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Registro con sensor digital", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(6.dp))

                fun saveValue(label: String, value: String) {
                    scope.launch {
                        when (label) {
                            "N" -> ConditionsLocalStore.saveNitrogen(context, value)
                            "P" -> ConditionsLocalStore.savePhosphorus(context, value)
                            "K" -> ConditionsLocalStore.savePotassium(context, value)
                            "MO" -> ConditionsLocalStore.saveOrganicMatter(context, value)
                            "CIC" -> ConditionsLocalStore.saveCIC(context, value)
                        }
                    }
                }

                listOf(
                    Triple("Nitrógeno (N)", nitrogen) { v: String ->
                        nitrogen = v; saveValue(
                        "N",
                        v
                    )
                    },
                    Triple("Fósforo (P)", phosphorus) { v: String ->
                        phosphorus = v; saveValue(
                        "P",
                        v
                    )
                    },
                    Triple("Potasio (K)", potassium) { v: String ->
                        potassium = v; saveValue(
                        "K",
                        v
                    )
                    },
                    Triple("Materia\norgánica", organicMatter) { v: String ->
                        organicMatter = v; saveValue("MO", v)
                    },
                    Triple("CIC", cic) { v: String -> cic = v; saveValue("CIC", v) }
                ).forEach { (label, value, onChange) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                        OutlinedTextField(
                            value = value,
                            onValueChange = {
                                if (it.all { c -> c.isDigit() || c == '.' }) onChange(
                                    it
                                )
                            },
                            placeholder = { Text("Valor") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = accent,
                                unfocusedBorderColor = borderSoft,
                                cursorColor = accent
                            ),
                            modifier = Modifier
                                .width(240.dp)
                                .padding(vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
            Button(
                onClick = { onNext?.invoke() },
                enabled = fertilityLevel.isNotBlank() || nitrogen.isNotBlank() || phosphorus.isNotBlank(),
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

@Preview(showBackground = true, backgroundColor = 0xFFF4F8EF)
@Composable
private fun PreviewConditionsScreen() {
    MaterialTheme { ConditionsScreen() }
}