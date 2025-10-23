package com.example.awaq_agromo.presentation.screens.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.R
import com.example.awaq_agromo.data.local.store.PhLocalStore
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhScreen(
    onNext: (() -> Unit)? = null,
    onManualClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Colores
    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    // Persistencia
    val savedPh by PhLocalStore.readPhValue(context).collectAsState(initial = null)
    val savedMethod by PhLocalStore.readPhMethod(context).collectAsState(initial = null)

    var phValue by remember { mutableStateOf(savedPh ?: "") }
    var phError by remember { mutableStateOf<String?>(null) }
    var method by remember { mutableStateOf(savedMethod ?: "") }

    fun validateAndSavePh(v: String) {
        val ok = v.toFloatOrNull()?.let { it in 0.0..14.0 } ?: false
        phError = if (ok || v.isEmpty()) null else "El pH debe estar entre 0 y 14"
        if (ok) scope.launch { PhLocalStore.savePhValue(context, v) }
    }

    Surface(
        modifier = Modifier.fillMaxSize().background(bgScreen),
        color = bgScreen
    ) {
        Column(Modifier.fillMaxSize()) {

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Medición de pH del suelo",
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
                HorizontalDotBar(n = 11, k = 4, modifier = Modifier.fillMaxWidth())
            }

            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Indique el nivel de pH del suelo y el método usado.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary
                )
            }

            // Imagen suelo
            Spacer(Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.suelo),
                contentDescription = "Medición de pH del suelo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 36.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            // Campo de pH
            Spacer(Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Nivel de pH", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = phValue,
                    onValueChange = { v ->
                        if (v.isEmpty() || v.matches(Regex("""^\d{0,2}(\.\d{0,2})?$"""))) {
                            phValue = v
                            validateAndSavePh(v)
                        }
                    },
                    placeholder = { Text("Número de (0–14)") },
                    isError = phError != null,
                    supportingText = { if (phError != null) Text(phError!!, color = Color(0xFF8B0000)) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accent,
                        unfocusedBorderColor = borderSoft,
                        cursorColor = accent
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Instructivos
            Spacer(Modifier.height(18.dp))
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Instructivos", color = textSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(8.dp))

                GuideItemWithImage(
                    title = "Medidor Digital",
                    subtitle = "Paso a paso para medición precisa",
                    imageRes = R.drawable.digital,
                    onClick = {
                        method = "Digital"
                        scope.launch { PhLocalStore.savePhMethod(context, method) }
                    },
                    accent = accent
                )

                Spacer(Modifier.height(8.dp))

                GuideItemWithImage(
                    title = "Cinta Reactiva",
                    subtitle = "Cómo usar tiras de pH correctamente",
                    imageRes = R.drawable.reactiva,
                    onClick = {
                        method = "Cinta"
                        scope.launch { PhLocalStore.savePhMethod(context, method) }
                    },
                    accent = accent
                )

                Spacer(Modifier.height(8.dp))

                GuideItemWithImage(
                    title = "Medición Manual",
                    subtitle = "Mezcla, reposo y lectura visual",
                    imageRes = R.drawable.manual,
                    onClick = {
                        method = "Manual"
                        scope.launch { PhLocalStore.savePhMethod(context, method) }
                    },
                    accent = accent
                )
            }

            Spacer(Modifier.height(18.dp))
            Button(
                onClick = { onNext?.invoke() },
                enabled = (phValue.toFloatOrNull()?.let { it in 0f..14f } == true) || method.isNotBlank(),
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

@Composable
private fun GuideItemWithImage(
    title: String,
    subtitle: String,
    imageRes: Int,
    onClick: () -> Unit,
    accent: Color
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, color = Color(0xFF2F312F))
                Spacer(Modifier.height(2.dp))
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color(0xFF7A8575))
            }
            IconButton(
                onClick = { onClick() }
            ) {
                Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = accent)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF4F8EF, widthDp = 360)
@Composable
private fun PhScreenPreview() {
    MaterialTheme { PhScreen() }
}