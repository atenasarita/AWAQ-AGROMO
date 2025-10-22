package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.awaq_agromo.data.local.store.FoliageLocalStore
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoliageScreen(
    onNext: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    val savedDensity by FoliageLocalStore.readDensity(context).collectAsState(initial = "")
    val savedColor by FoliageLocalStore.readColor(context).collectAsState(initial = "")
    val savedCondition by FoliageLocalStore.readCondition(context).collectAsState(initial = "")

    var density by remember { mutableStateOf(savedDensity) }
    var color by remember { mutableStateOf(savedColor) }
    var condition by remember { mutableStateOf(savedCondition) }

    var expandedDensity by remember { mutableStateOf(false) }
    var expandedColor by remember { mutableStateOf(false) }
    var expandedCondition by remember { mutableStateOf(false) }

    val densityOptions = listOf("Poco", "Mitad", "Total (nada de suelo visible)")
    val colorOptions = listOf("Verde oscuro (sano)", "Verde claro / Amarillo (débil)", "Marrón (seco)")
    val conditionOptions = listOf("Uniforme", "Irregular")

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
                    "Desarrollo de la planta",
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
                HorizontalDotBar(n = 13, k = 6, modifier = Modifier.fillMaxWidth())
            }
            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Follaje",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textPrimary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Observe el follaje: ¿cuánto suelo se ve (poco, mitad o nada)? ¿De qué color son las hojas (verde oscuro - sano, claro/amarillo - débil, marrón - seco)? ¿Las plantas son uniformes o irregulares?",
                    style = MaterialTheme.typography.bodySmall,
                    color = textSecondary
                )
            }

            Spacer(Modifier.height(12.dp))

            Image(
                painter = painterResource(id = R.drawable.seed_illustration),
                contentDescription = "Ilustración follaje",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 36.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Spacer(Modifier.height(6.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedDensity,
                    onExpandedChange = { expandedDensity = it }
                ) {
                    OutlinedTextField(
                        value = density,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Densidad del follaje") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDensity) },
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
                        expanded = expandedDensity,
                        onDismissRequest = { expandedDensity = false }
                    ) {
                        densityOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    density = opt
                                    expandedDensity = false
                                    scope.launch { FoliageLocalStore.saveDensity(context, opt) }
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(22.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedColor,
                    onExpandedChange = { expandedColor = it }
                ) {
                    OutlinedTextField(
                        value = color,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Color predominante del follaje") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedColor) },
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
                        expanded = expandedColor,
                        onDismissRequest = { expandedColor = false }
                    ) {
                        colorOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    color = opt
                                    expandedColor = false
                                    scope.launch { FoliageLocalStore.saveColor(context, opt) }
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(22.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedCondition,
                    onExpandedChange = { expandedCondition = it }
                ) {
                    OutlinedTextField(
                        value = condition,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Estado general del follaje") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCondition) },
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
                        expanded = expandedCondition,
                        onDismissRequest = { expandedCondition = false }
                    ) {
                        conditionOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    condition = opt
                                    expandedCondition = false
                                    scope.launch { FoliageLocalStore.saveCondition(context, opt) }
                                }
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { onNext?.invoke() },
                enabled = density.isNotBlank() && color.isNotBlank() && condition.isNotBlank(),
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
private fun PreviewFoliageScreen() {
    MaterialTheme { FoliageScreen() }
}