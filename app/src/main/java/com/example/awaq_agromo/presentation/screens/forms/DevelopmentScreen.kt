package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.awaq_agromo.data.local.store.DevelopmentLocalStore
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import kotlinx.coroutines.launch
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevelopmentScreen(
    onNext: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    val savedDate by DevelopmentLocalStore.readDate(context).collectAsState(initial = "")
    val savedMethod by DevelopmentLocalStore.readMethod(context).collectAsState(initial = "")
    val savedPlantNumber by DevelopmentLocalStore.readPlantNumber(context).collectAsState(initial = "")
    val savedHeight by DevelopmentLocalStore.readHeight(context).collectAsState(initial = "")

    var date by remember { mutableStateOf(savedDate) }
    var methodExpanded by remember { mutableStateOf(false) }
    var method by remember { mutableStateOf(savedMethod) }
    var plantNumber by remember { mutableStateOf(savedPlantNumber) }
    var height by remember { mutableStateOf(savedHeight) }

    val methodOptions = listOf("Manual", "Sensor digital", "Fotografía")

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _, y, m, d ->
            val formatted = "%02d/%02d/%04d".format(d, m + 1, y)
            date = formatted
            scope.launch { DevelopmentLocalStore.saveDate(context, formatted) }
        },
        year,
        month,
        day
    )

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
                HorizontalDotBar(n = 13, k = 4, modifier = Modifier.fillMaxWidth())
            }
            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Altura de la planta",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textPrimary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Mida la altura promedio de las plantas, tomando como referencia un valor promedio (no la más baja ni la más alta).",
                    style = MaterialTheme.typography.bodySmall,
                    color = textSecondary
                )
            }

            Spacer(Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.plant_growth),
                contentDescription = "Altura planta",
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
                OutlinedTextField(
                    value = date,
                    onValueChange = {
                        date = it
                        scope.launch { DevelopmentLocalStore.saveDate(context, it) }
                    },
                    placeholder = { Text("dd/mm/aaaa") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accent,
                        unfocusedBorderColor = borderSoft,
                        cursorColor = accent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = methodExpanded,
                    onExpandedChange = { methodExpanded = it }
                ) {
                    OutlinedTextField(
                        value = method,
                        onValueChange = { },
                        readOnly = true,
                        placeholder = { Text("Método de medición") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = methodExpanded) },
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
                        expanded = methodExpanded,
                        onDismissRequest = { methodExpanded = false }
                    ) {
                        methodOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    method = opt
                                    methodExpanded = false
                                    scope.launch { DevelopmentLocalStore.saveMethod(context, opt) }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Número de la planta",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black.copy(alpha = 0.8f)
                )
                OutlinedTextField(
                    value = plantNumber,
                    onValueChange = {
                        plantNumber = it
                        scope.launch { DevelopmentLocalStore.savePlantNumber(context, it) }
                    },
                    placeholder = { Text("Ej. planta 1, planta 2...") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accent,
                        unfocusedBorderColor = borderSoft,
                        cursorColor = accent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Altura registrada",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black.copy(alpha = 0.8f)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = height,
                    onValueChange = {
                        if (it.all { c -> c.isDigit() || c == '.' }) {
                            height = it
                            scope.launch { DevelopmentLocalStore.saveHeight(context, it) }
                        }
                    },
                    placeholder = { Text("Número") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accent,
                        unfocusedBorderColor = borderSoft,
                        cursorColor = accent
                    ),
                    suffix = { Text("cm") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = { onNext?.invoke() },
                enabled = date.isNotBlank() && method.isNotBlank() && height.isNotBlank(),
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
private fun PreviewDevelopmentScreen() {
    MaterialTheme { DevelopmentScreen() }
}
