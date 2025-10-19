package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.awaq_agromo.data.local.store.CropLocalStore
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariedadScreen(
    onNext: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Colores de tu app
    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    // Lista base
    val baseCrops = remember {
        listOf(
            "Algodón", "Arroz", "Café", "Cacao", "Cebada", "Maíz", "Trigo",
            "Sorgo", "Papa", "Frijol", "Aguacate", "Caña de azúcar", "Cítricos"
        )
    }

    // Estados persistidos
    val selected by CropLocalStore.readSelected(context).collectAsState(initial = emptySet())
    val savedDate by CropLocalStore.readSowingDate(context).collectAsState(initial = null)

    // Estados locales
    var query by remember { mutableStateOf("") }
    var sowingDateText by remember { mutableStateOf("") }
    var openDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Rellenar fecha guardada
    LaunchedEffect(savedDate) {
        if (!savedDate.isNullOrBlank()) sowingDateText = savedDate!!
    }

    // Filtrado simple
    val filtered = remember(query, baseCrops, selected) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) baseCrops else baseCrops.filter { it.lowercase().contains(q) }
    }

    Surface(
        modifier = Modifier.fillMaxSize().background(bgScreen),
        color = bgScreen
    ) {
        Column(Modifier.fillMaxSize()) {

            Spacer(Modifier.height(18.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Información general del cultivo",
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
                HorizontalDotBar(n = 11, k = 2, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                Text(
                    "Variedad Cultivada",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = textPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "¿Qué cultivo y variedad tiene sembrado?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary
                )
            }

            // Imagen decorativa 🌾
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.agri),
                contentDescription = "Imagen agrícola",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 40.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(20.dp))

            // Búsqueda y agregar manualmente
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Seleccione el cultivo de la lista o ingréselo manualmente.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Busca por cultivo") },
                        leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Buscar") },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accent,
                            unfocusedBorderColor = borderSoft,
                            cursorColor = accent
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val clean = query.trim()
                            if (clean.isNotEmpty()) {
                                scope.launch { CropLocalStore.addOne(context, clean) }
                                query = ""
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accent)
                    ) { Text("Agregar") }
                }
            }

            // Chips seleccionadas
            AnimatedVisibility(visible = selected.isNotEmpty()) {
                FlowRowChips(
                    items = selected.toList(),
                    accent = accent,
                    textPrimary = textPrimary,
                    borderSoft = borderSoft,
                    onRemove = { name -> scope.launch { CropLocalStore.removeOne(context, name) } }
                )
            }

            // Lista con checkboxes
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .border(1.dp, borderSoft, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                LazyColumn(contentPadding = PaddingValues(vertical = 6.dp), modifier = Modifier.fillMaxSize()) {
                    items(filtered, key = { it }) { item ->
                        CropCheckRow(
                            name = item,
                            checked = selected.contains(item),
                            onToggle = { scope.launch { CropLocalStore.toggle(context, item) } },
                            textPrimary = textPrimary,
                            borderSoft = borderSoft
                        )
                    }
                }
            }

            // Fecha de siembra persistente
            Spacer(Modifier.height(12.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Fecha de la siembra",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textPrimary
                )
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = sowingDateText,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("dd/mm/aaaa") },
                    trailingIcon = {
                        IconButton(onClick = { openDatePicker = true }) {
                            Icon(Icons.Outlined.DateRange, contentDescription = "Seleccionar fecha")
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accent,
                        unfocusedBorderColor = borderSoft,
                        cursorColor = accent
                    ),
                    modifier = Modifier.fillMaxWidth().clickable { openDatePicker = true }
                )
            }

            if (openDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { openDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val millis = datePickerState.selectedDateMillis
                            if (millis != null) {
                                val chosen = millis.toDateText()
                                sowingDateText = chosen
                                scope.launch { CropLocalStore.saveSowingDate(context, chosen) }
                            }
                            openDatePicker = false
                        }) { Text("Aceptar") }
                    },
                    dismissButton = {
                        TextButton(onClick = { openDatePicker = false }) { Text("Cancelar") }
                    }
                ) { DatePicker(state = datePickerState) }
            }

            // Footer CTA
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = { onNext?.invoke() },
                enabled = selected.isNotEmpty(),
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
        }
    }
}

/* ------------------- Helpers y Preview ------------------- */

@Composable
private fun CropCheckRow(
    name: String,
    checked: Boolean,
    onToggle: () -> Unit,
    textPrimary: Color,
    borderSoft: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .border(1.dp, borderSoft, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(name, style = MaterialTheme.typography.titleMedium, color = textPrimary, modifier = Modifier.weight(1f))
        Checkbox(checked = checked, onCheckedChange = { onToggle() })
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun FlowRowChips(
    items: List<String>,
    accent: Color,
    textPrimary: Color,
    borderSoft: Color,
    onRemove: (String) -> Unit
) {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        val grouped = items.chunked(3)
        grouped.forEach { row ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                row.forEach { name ->
                    Chip(label = name, accent, textPrimary, borderSoft) { onRemove(name) }
                    Spacer(Modifier.width(8.dp))
                }
            }
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Composable
private fun Chip(
    label: String,
    accent: Color,
    textPrimary: Color,
    borderSoft: Color,
    onRemove: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, borderSoft, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(label, color = textPrimary, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.width(6.dp))
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.12f))
                .clickable { onRemove() },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Close, contentDescription = "Quitar", tint = accent)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF4F8EF, widthDp = 360)
@Composable
private fun VariedadScreenPreview() {
    MaterialTheme { VariedadScreen() }
}

private fun Long.toDateText(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}