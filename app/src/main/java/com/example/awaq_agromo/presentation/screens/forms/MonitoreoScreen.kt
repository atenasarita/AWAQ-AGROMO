package com.example.awaq_agromo.presentation.screens.forms

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.awaq_agromo.R
import com.example.awaq_agromo.data.local.store.WeatherLocalStore
import com.example.awaq_agromo.data.local.store.WeatherSnapshot
import com.example.awaq_agromo.presentation.component.ui.HorizontalDotBar
import com.example.awaq_agromo.presentation.theme.AgromoTheme
import com.example.awaq_agromo.presentation.theme.PrincipalPrimary
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.collections.isNullOrEmpty
import kotlin.collections.joinToString
import kotlin.let
import kotlin.text.format
import kotlin.text.ifBlank
import kotlin.text.isNullOrBlank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoreoScreen(
    onNext: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val fused = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // STATE
    var place by remember { mutableStateOf<String?>(null) } // Se actualiza con "Ciudad, Estado" o lat/lon
    var manualCity by remember { mutableStateOf("") }
    var isRequesting by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    // Observa el snapshot local cuando ya exista "place"
    val snapshotFlow = remember(place) {
        if (place.isNullOrBlank()) flowOf<WeatherSnapshot?>(null)
        else WeatherLocalStore.readSnapshot(context, place!!)
    }
    val snapshot by snapshotFlow.collectAsState(initial = null)

    // Launcher permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val granted = (perms[Manifest.permission.ACCESS_FINE_LOCATION] == true) ||
                (perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true)
        if (!granted) {
            errorMsg = "Permiso de ubicación denegado."
            isRequesting = false
        } else {
            requestCurrentLocation(
                fused = fused,
                onStart = { isRequesting = true },
                onResult = { lat, lon ->
                    scope.launch {
                        val pretty = withContext(Dispatchers.IO) {
                            reverseGeocodeCityState(lat, lon, context)
                        }
                        val label = pretty ?: "${"%.5f".format(lat)}, ${"%.5f".format(lon)}"
                        place = label
                        errorMsg = null

                        // Guarda snapshot local con los valores del mock (de tu imagen)
                        WeatherLocalStore.saveSnapshot(
                            context,
                            WeatherSnapshot(
                                locationLabel = label,
                                humidityPct = 48,
                                windKmh = 10,
                                rainPct = 22,
                                tempC = 23,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        isRequesting = false
                    }
                },
                onError = { msg ->
                    errorMsg = msg
                    isRequesting = false
                }
            )
        }
    }

    // Colores de la app
    val bgScreen = Color(0xFFF4F8EF)
    val textPrimary = Color(0xFF1D1D1D)
    val textSecondary = Color(0xFF424842)
    val borderSoft = Color(0xFFBBD8A8)
    val accent = PrincipalPrimary

    AgromoTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(bgScreen),
            color = bgScreen
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // HEADER
                Spacer(Modifier.height(18.dp))
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "Registro del Cultivo",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = textPrimary
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Complete los datos que disponga; el resto puede omitirlo.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = textSecondary
                    )
                    Spacer(Modifier.height(12.dp))
                    HorizontalDotBar(
                        n = 8, k = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, bottom = 18.dp)
                    )
                    Text(
                        text = "Indique la ubicación de su cultivo",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = textPrimary
                    )
                }

                // CARD principal
                Spacer(Modifier.height(12.dp))
                CardBox(
                    borderSoft = borderSoft
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ubi),
                        contentDescription = "Ubicación",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(bgScreen),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = accent
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = place ?: "Ubicación no establecida",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = textPrimary
                        )
                    }

                    // Muestra las métricas si ya hay snapshot guardado
                    AnimatedVisibility(visible = snapshot != null) {
                        Spacer(Modifier.height(12.dp))
                        snapshot?.let { s ->
                            WeatherStatsRow(
                                humidity = s.humidityPct,
                                windKmh = s.windKmh,
                                rainPct = s.rainPct,
                                tempC = s.tempC
                            )
                        }
                    }

                    AnimatedVisibility(visible = errorMsg != null) {
                        Text(
                            text = errorMsg ?: "",
                            color = Color(0xFF8B0000),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            if (!hasLocationPermission(context)) {
                                isRequesting = true
                                permissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                                return@OutlinedButton
                            }
                            requestCurrentLocation(
                                fused = fused,
                                onStart = { isRequesting = true },
                                onResult = { lat, lon ->
                                    scope.launch {
                                        val pretty = withContext(Dispatchers.IO) {
                                            reverseGeocodeCityState(lat, lon, context)
                                        }
                                        val label =
                                            pretty ?: "${"%.5f".format(lat)}, ${"%.5f".format(lon)}"
                                        place = label
                                        errorMsg = null

                                        WeatherLocalStore.saveSnapshot(
                                            context,
                                            WeatherSnapshot(
                                                locationLabel = label,
                                                humidityPct = 48,
                                                windKmh = 10,
                                                rainPct = 22,
                                                tempC = 23,
                                                timestamp = System.currentTimeMillis()
                                            )
                                        )
                                        isRequesting = false
                                    }
                                },
                                onError = { msg ->
                                    errorMsg = msg
                                    isRequesting = false
                                }
                            )
                        },
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = accent),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        if (isRequesting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = accent
                            )
                            Spacer(Modifier.width(12.dp))
                            Text("Obteniendo ubicación…")
                        } else {
                            Text("Usar mi ubicación actual")
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "O complete manualmente",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = textSecondary
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = manualCity,
                        onValueChange = { manualCity = it },
                        placeholder = { Text("Busca por ciudad") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = "Buscar")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accent,
                            unfocusedBorderColor = borderSoft,
                            cursorColor = accent,
                            focusedLabelColor = accent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // FOOTER CTA
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { onNext?.invoke() },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accent)
                ) {
                    Text("Continuar")
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

/* ------------------------- UI helpers ------------------------- */

@Composable
private fun CardBox(
    borderSoft: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(2.dp, RoundedCornerShape(24.dp), clip = true)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(1.dp, borderSoft, RoundedCornerShape(24.dp))
            .padding(16.dp),
        content = content
    )
}

@Composable
private fun WeatherStatsRow(
    humidity: Int,
    windKmh: Int,
    rainPct: Int,
    tempC: Int
) {
    val titleColor = Color(0xFF9EAD99)   // gris verdoso claro (como en tu mock)
    val valueColor = Color(0xFF2F312F)   // gris oscuro legible

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatCol("Humedad", "$humidity%", titleColor, valueColor)
        StatCol("Viento", "${windKmh} Km/h", titleColor, valueColor)
        StatCol("Lluvias", "$rainPct%", titleColor, valueColor)
        StatCol("Temperatura", "${tempC}°C", titleColor, valueColor)
    }
}

@Composable
private fun StatCol(
    title: String,
    value: String,
    titleColor: Color,
    valueColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = titleColor, style = MaterialTheme.typography.labelMedium)
        Spacer(Modifier.height(2.dp))
        Text(value, color = valueColor, style = MaterialTheme.typography.titleMedium)
    }
}

/* ------------------------- Permisos / ubicación ------------------------- */

private fun hasLocationPermission(ctx: Context): Boolean {
    val fine = ContextCompat.checkSelfPermission(
        ctx, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val coarse = ContextCompat.checkSelfPermission(
        ctx, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    return fine || coarse
}

private fun requestCurrentLocation(
    fused: FusedLocationProviderClient,
    onStart: () -> Unit,
    onResult: (lat: Double, lon: Double) -> Unit,
    onError: (String) -> Unit
) {
    onStart()
    try {
        @SuppressLint("MissingPermission")
        val task = fused.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            /* cancellationToken = */ null
        )
        task.addOnSuccessListener { loc ->
            if (loc != null) onResult(loc.latitude, loc.longitude)
            else onError("No se pudo obtener la ubicación.")
        }.addOnFailureListener { e ->
            onError("Error: ${e.message ?: "ubicación no disponible"}")
        }
    } catch (se: SecurityException) {
        onError("Permiso de ubicación requerido.")
    } catch (e: Exception) {
        onError("Error: ${e.message ?: "ubicación no disponible"}")
    }
}

private suspend fun reverseGeocodeCityState(
    lat: Double,
    lon: Double,
    context: Context
): String? = withContext(Dispatchers.IO) {
    try {
        val geocoder = Geocoder(context, Locale.getDefault())

        @Suppress("DEPRECATION")
        val result = geocoder.getFromLocation(lat, lon, 1)
        if (!result.isNullOrEmpty()) {
            val a = result[0]
            val city = a.locality ?: a.subAdminArea
            val state = a.adminArea ?: a.subAdminArea
            listOfNotNull(city, state).joinToString(", ").ifBlank { null }
        } else null
    } catch (e: Exception) {
        null
    }
}

/* ------------------------- Preview ------------------------- */

@Preview(showBackground = true, backgroundColor = 0xFFF4F8EF)
@Composable
private fun MonitoreoScreenPreview() {
    // Preview estático: no usa GPS ni DataStore
    MaterialTheme {
        MonitoreoScreen(onNext = null)
    }
}