package com.example.awaq_agromo.presentation.screens.forms

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.awaq_agromo.data.local.store.WeatherLocalStore
import com.example.awaq_agromo.data.local.store.WeatherSnapshot
import com.example.awaq_agromo.data.remote.api.MonitoreoViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.awaq_agromo.R

/* Paleta del diseño */
private val BgLight = Color(0xFFF4F8EF)
private val SoftGreen = Color(0xFFDDEAD0)
private val AccentBorder = Color(0xFFBBD8A8)
private val GreenDark = Color(0xFF2E4A1F)
private val TextMuted = Color(0xFF4B5563)

@Composable
fun MonitoreoScreen(
    onNext: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val vm: MonitoreoViewModel = hiltViewModel()

    // Mueve a BuildConfig cuando gustes
    val openWeatherApiKey = "df02eb8f0cefd35cb63747c5c060dcc2"

    var place by remember { mutableStateOf<String?>(null) }
    var isRequesting by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    var manualCity by remember { mutableStateOf(TextFieldValue("")) }

    val loading by vm.loading.collectAsState()
    val repoError by vm.error.collectAsState()

    val snapshotFlow = remember(place) {
        if (place.isNullOrBlank()) flowOf<WeatherSnapshot?>(null)
        else WeatherLocalStore.readSnapshot(context, place!!)
    }
    val snapshot by snapshotFlow.collectAsState(initial = null)

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            scope.launch {
                requestCurrentLocationAndFetch(
                    context = context,
                    onResolved = { label, lat, lon ->
                        place = label
                        vm.fetchAndSave(context, label, lat, lon, openWeatherApiKey)
                        isRequesting = false
                    },
                    onError = { msg ->
                        errorMsg = msg
                        isRequesting = false
                    }
                )
            }
        } else {
            errorMsg = "Permiso de ubicación denegado"
            isRequesting = false
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = BgLight) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Header
            Text("Registro del Cultivo", fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(Modifier.height(4.dp))
            Text("Complete los datos que disponga; el resto puede omitirlo.", color = TextMuted)

            Spacer(Modifier.height(8.dp))
            DotBar(n = 10, k = 2) // indicador de progreso similar al de tu captura

            Spacer(Modifier.height(12.dp))
            Text("Indique la ubicación de su cultivo", color = Color.Black, fontWeight = FontWeight.SemiBold)

            // Ilustración estilo hero (sin depender de drawables)
            Spacer(Modifier.height(12.dp))
            HeroIllustration()

            Spacer(Modifier.height(12.dp))

            // Fila de ubicación (icono + texto)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .border(BorderStroke(1.dp, AccentBorder), RoundedCornerShape(24.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = GreenDark)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = place ?: "Ubicación no establecida",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(12.dp))

            // Botón outlined "Usar mi ubicación actual"
            OutlinedButton(
                onClick = {
                    errorMsg = null
                    isRequesting = true
                    permissionsLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, AccentBorder),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenDark),
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading && !isRequesting
            ) {
                if (isRequesting) {
                    CircularProgressIndicator()
                } else {
                    Text("Usar mi ubicación actual")
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("O complete manualmente", color = TextMuted)

            Spacer(Modifier.height(8.dp))

            // Campo búsqueda con icono de lupa
            OutlinedTextField(
                value = manualCity,
                onValueChange = { manualCity = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Busca por ciudad") },
                trailingIcon = {
                    Icon(Icons.Outlined.Search, contentDescription = "Buscar", tint = TextMuted)
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentBorder,
                    unfocusedBorderColor = AccentBorder
                )
            )

            // Mensaje de error
            AnimatedVisibility(visible = errorMsg != null || repoError != null, enter = fadeIn(), exit = fadeOut()) {
                Spacer(Modifier.height(8.dp))
                Text(text = errorMsg ?: repoError ?: "", color = Color(0xFF991B1B))
            }

            // Bloque de stats (opcional; si quieres ocultarlo, comenta esta sección)
            AnimatedVisibility(visible = snapshot != null, enter = fadeIn(), exit = fadeOut()) {
                val s = snapshot!!
                Spacer(Modifier.height(10.dp))
                StatsChipRow(
                    temp = "${s.tempC} °C",
                    hum = "${s.humidityPct} %",
                    wind = "${s.windKmh} km/h",
                    rain = "${s.rainPct} %"
                )
            }

            Spacer(Modifier.weight(1f))

            // Botón Siguiente (píldora verde)
            Button(
                onClick = { onNext?.invoke() },
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Siguiente")
            }
        }
    }
}

/* --- UI helpers para igualar tu diseño --- */

@Composable
private fun DotBar(n: Int, k: Int) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        repeat(n) { i ->
            val active = i < k
            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (active) GreenDark else AccentBorder.copy(alpha = 0.5f))
            )
        }
    }
}

@Composable
private fun HeroIllustration() {
    val painter = painterResource(id = R.drawable.ubi)

    // Calcula relación de aspecto real de la imagen (fallback a 16:9 si no está disponible)
    val aspect = remember(painter) {
        val s = painter.intrinsicSize
        if (s.width > 0f && s.height > 0f) s.width / s.height else 16f / 9f
    }

    // Card con bordes redondeados, sombra suave y fondo acorde a tu paleta
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspect)           // respeta la proporción -> no se recorta
            .padding(top = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SoftGreen),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, AccentBorder)
    ) {
        // La imagen se ajusta dentro del contenedor sin recortarse
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),           // respiración alrededor
            contentScale = ContentScale.Fit, // SIN recorte (fit dentro del área)
            alignment = Alignment.Center
        )
    }
}

@Composable
private fun StatsChipRow(temp: String, hum: String, wind: String, rain: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatChip("Temp.", temp)
        StatChip("Humedad", hum)
        StatChip("Viento", wind)
        StatChip("Lluvia", rain)
    }
}

@Composable
private fun StatChip(label: String, value: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        tonalElevation = 0.dp,
        border = BorderStroke(1.dp, AccentBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, color = TextMuted)
            Spacer(Modifier.width(6.dp))
            Text(value, color = GreenDark, fontWeight = FontWeight.SemiBold)
        }
    }
}

/* --- Ubicación + geocoder + Task.await sin play-services coroutines --- */

@SuppressLint("MissingPermission")
private suspend fun requestCurrentLocationAndFetch(
    context: android.content.Context,
    onResolved: (label: String, lat: Double, lon: Double) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val fused = LocationServices.getFusedLocationProviderClient(context)
        val priority = Priority.PRIORITY_HIGH_ACCURACY

        val loc: Location? = fused.getCurrentLocation(priority, /* cancellationToken = */ null).awaitCatching()
        if (loc == null) {
            onError("No se pudo obtener la ubicación")
            return
        }

        val lat = loc.latitude
        val lon = loc.longitude

        val label = reverseGeocodeCityState(context, lat, lon)
            ?: "${"%.5f".format(lat)}, ${"%.5f".format(lon)}"

        onResolved(label, lat, lon)
    } catch (t: Throwable) {
        onError(t.message ?: "Error de ubicación")
    }
}

/** Espera de Task sin kotlinx-coroutines-play-services */
private suspend fun <T> Task<T>.awaitCatching(): T? =
    suspendCancellableCoroutine { cont ->
        addOnCompleteListener { task ->
            if (task.isSuccessful) cont.resume(task.result) else cont.resume(null)
        }
    }

private suspend fun reverseGeocodeCityState(
    context: android.content.Context,
    lat: Double,
    lon: Double
): String? = kotlinx.coroutines.withContext(Dispatchers.IO) {
    try {
        @Suppress("DEPRECATION")
        val geocoder = Geocoder(context, Locale.getDefault())
        val list = geocoder.getFromLocation(lat, lon, 1)
        if (!list.isNullOrEmpty()) {
            val addr = list[0]
            val city = addr.locality ?: addr.subAdminArea
            val state = addr.adminArea
            when {
                city != null && state != null -> "$city, $state"
                city != null -> city
                state != null -> state
                else -> null
            }
        } else null
    } catch (_: Exception) {
        null
    }
}