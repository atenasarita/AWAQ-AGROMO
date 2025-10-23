package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.awaq_agromo.R
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import kotlinx.coroutines.launch
import com.example.awaq_agromo.data.local.store.CropLocalStore
import com.example.awaq_agromo.presentation.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.chunked
import kotlin.collections.filter
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import kotlin.collections.toList
import kotlin.text.contains
import kotlin.text.isEmpty
import kotlin.text.isNotEmpty
import kotlin.text.isNullOrBlank
import kotlin.text.lowercase
import kotlin.text.trim

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariedadScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    onNext: (() -> Unit)? = null
) {
    val user by userViewModel.user.collectAsState()
    val userId = user?.id
    val crops by userViewModel.crops.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.fetchCurrentUser()  // ensures user is loaded
    }

    var selectedCrops by remember { mutableStateOf(setOf<String>()) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Estados persistidos
    val savedDate by CropLocalStore.readSowingDate(context).collectAsState(initial = null)

    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    // Estados locales
    var query by remember { mutableStateOf("") }
    var sowingDateText by remember { mutableStateOf("") }
    var openDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Log userId to debug
    LaunchedEffect(userId) {
        Log.d("VariedadesScreen", "Current userId: $userId")
        if (userId != null) {
            userViewModel.loadCropsForUser(userId)
        }
    }

    LaunchedEffect(savedDate) {
        if (!savedDate.isNullOrBlank()) sowingDateText = savedDate!!
    }

    val filtered = remember(query, crops) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) crops.toList() else crops.filter { it.lowercase().contains(q) }
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
                            if (clean.isNotEmpty() && userId != null) {
                                userViewModel.addCrop(clean)
                                selectedCrops = selectedCrops + clean
                                query = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = accent)
                    ) {
                        Text("Agregar")
                    }
                }
            }



            AnimatedVisibility(visible = selectedCrops.isNotEmpty()) {
                FlowRowChips(
                    items = selectedCrops.toList(),
                    accent = accent,
                    textPrimary = textPrimary,
                    borderSoft = borderSoft
                )
            }

            Spacer(Modifier.height(6.dp))

// 🔹 Box with all crops (userViewModel.crops)
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
                        val isChecked = selectedCrops.contains(item)
                        CropCheckRow(
                            name = item,
                            checked = isChecked,
                            onToggle = {
                                val newSet = selectedCrops.toMutableSet().apply {
                                    if (isChecked) remove(item) else add(item)
                                }
                                selectedCrops = newSet
                            },
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

            Spacer(Modifier.height(10.dp))
            Button(
                onClick = { onNext?.invoke() },
                enabled = crops.isNotEmpty(),
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
) {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        val grouped = items.chunked(3)
        grouped.forEach { row ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                row.forEach { name ->
                    Chip(label = name, textPrimary, borderSoft)
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
    textPrimary: Color,
    borderSoft: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, borderSoft, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(label, color = textPrimary, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.width(6.dp))
    }
}



private fun Long.toDateText(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}