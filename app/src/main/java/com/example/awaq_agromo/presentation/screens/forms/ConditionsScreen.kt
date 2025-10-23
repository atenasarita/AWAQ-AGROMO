package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.awaq_agromo.R
import com.example.awaq_agromo.presentation.component.buttons.AgromoPrimaryButton
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.Neutral400
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import kotlin.collections.forEach



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConditionsScreen(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    onNext: () -> Unit = {}
) {
    var selectedOption by remember { mutableStateOf("Nivel de fertilidad") }
    var expanded by remember { mutableStateOf(false) }
    val opcionesFertilidad = listOf("Bajo", "Medio", "Alto")

    var nitrogen by remember { mutableStateOf("") }
    var phosphorus by remember { mutableStateOf("") }
    var potassium by remember { mutableStateOf("") }
    var organicMatter by remember { mutableStateOf("") }
    var cic by remember { mutableStateOf("") }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6FCE7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Suelo y condiciones",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = "Completa los datos que dispongas; el resto puede omitirse.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Start
            )

            HorizontalDotBar(
                n = 13,
                k = 4,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ---- UBICACIÓN Y CLIMA ----
            SectionTitle("Ubicación y clima")
            Text(
                text = "Ubicación de el cultivo y condiciones climaticas al momento de toma de datos.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            DisabledTextField("Ubicación", "Bucaramanga, Santander COLOMBIA")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ClimateBox("Humedad", "48%")
                ClimateBox("Viento", "10 km/h")
                ClimateBox("Lluvias", "22%")
                ClimateBox("Temp.", "23°C")
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, color = Color.LightGray)
            Spacer(modifier = Modifier.height(20.dp))

            // ---- VARIEDAD CULTIVADA ----
            SectionTitle("Variedad cultivada")
            DisabledTextField("Variedad", "Maíz")
            DisabledTextField("Fecha de la siembra", "12/08/2025")

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, color = Color.LightGray)
            Spacer(modifier = Modifier.height(20.dp))

            // ---- HUMEDAD DEL SUELO ----
            SectionTitle("Humedad del suelo")
            DisabledTextField("Con sensor digital", "Humedad 67%")

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, color = Color.LightGray)
            Spacer(modifier = Modifier.height(20.dp))

            // ---- FERTILIDAD DEL SUELO ----
            SectionTitle("Fertilidad del suelo")
            Text(
                text = "Indique la fertilidad del suelo, ya sea mediante análisis digital o de forma manual.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.soil_illustration),
                contentDescription = "Ilustración suelo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ---- REGISTRO MANUAL ----
            Text(
                text = "Registro manual",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(6.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Seleccionar nivel") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedBorderColor = PrincipalPrimary,
                        unfocusedBorderColor = Neutral400,
                        cursorColor = PrincipalPrimary,
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opcionesFertilidad.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedOption = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---- REGISTRO CON SENSOR DIGITAL ----
            Text(
                text = "Registro con sensor digital",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black
            )
            LabeledNumberTextField(
                label = "Nitrógeno (N)",
                value = nitrogen,
                onValueChange = { nitrogen = it },
                placeholder = "Valor mg/kg"
            )
            LabeledNumberTextField(
                label = "Fósforo (P)",
                value = phosphorus,
                onValueChange = { phosphorus = it },
                placeholder = "Valor mg/kg"
            )
            LabeledNumberTextField(
                label = "Potasio (K)",
                value = potassium,
                onValueChange = { potassium = it },
                placeholder = "Valor mg/kg"
            )
            LabeledNumberTextField(
                label = "Materia\norgánica",
                value = organicMatter,
                onValueChange = { organicMatter = it },
                placeholder = "Valor %"
            )
            LabeledNumberTextField(
                label = "CIC",
                value = cic,
                onValueChange = { cic = it },
                placeholder = "Valor cmol/kg"
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ---- BOTÓN SIGUIENTE ----
            AgromoPrimaryButton(
                text = "Siguiente",
                onClick = onNext
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// ------- COMPONENTES AUXILIARES PUESTOS EN EL MISMO ARCHIVO POR AHORA POR CONVENIENCA -------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledNumberTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
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
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = PrincipalPrimary,
                unfocusedBorderColor = Neutral400,
                cursorColor = PrincipalPrimary,
            ),
            placeholder = { Text(placeholder) },
            modifier = Modifier.width(240.dp)
        )
    }
}


@Composable
fun DisabledTextField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF000000).copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField( // dependencia de gradle
            value = value,
            onValueChange = {},
            singleLine = true,
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF2F2F2),
                unfocusedContainerColor = Color(0xFFF2F2F2),
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                cursorColor = Color.Transparent,
                disabledBorderColor = Color(0xFFE0E0E0)
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        color = Color.Black,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

// Caja de clima (temperatura, humedad, etc.)
@Composable
fun ClimateBox(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(70.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlantBoxPreview() {
    ConditionsScreen(
        selectedOption = "", // valor por defecto vacío
        onOptionSelected = {} // acción vacía para preview
    )
}
