package com.example.awaq_agromo.presentation.screens.perfil

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.awaq_agromo.R
import com.example.awaq_agromo.data.local.store.WeatherLocalStore
import com.example.awaq_agromo.data.local.store.WeatherSnapshot
import com.example.awaq_agromo.data.remote.api.MonitoreoViewModel
import com.example.awaq_agromo.presentation.component.profile.InfoCard
import com.example.awaq_agromo.presentation.component.profile.UserCard
import com.example.awaq_agromo.presentation.component.texts.TitleText
import com.example.awaq_agromo.presentation.model.InformeData
import com.example.awaq_agromo.presentation.screens.dashboard.sampleInformesRecientes
import com.example.awaq_agromo.presentation.screens.forms.requestCurrentLocationAndFetch
import com.example.awaq_agromo.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

val sampleInformes: List<InformeData> = listOf(
    InformeData(
        "12 sept",
        "Informe integral",
        "Atender",
        Color.Red,
        R.drawable.image_ph,
        "Calabaza"
    ),
    InformeData("15 sept", "Revisión hortalizas", "En curso", Color.Blue, R.drawable.image_ph, "Pimiento"),
    InformeData("20 sept", "Detección de plaga", "Urgente", Color.Red, R.drawable.planta_de_pimientos, "Calabaza"),
    InformeData("25 sept", "Abono orgánico", "Pendiente", Color.Gray, R.drawable.image_ph, "Berenjena"),
    InformeData("28 sept", "Ajuste de riego", "Completado", Color.Green, R.drawable.planta_de_pimientos, "Aceituna"),
    InformeData("01 oct", "Mantenimiento", "En curso", Color.Blue, R.drawable.planta_de_pimientos, "Chili"),
    InformeData("05 oct", "Fertilización", "Pendiente", Color.Gray, R.drawable.image_ph, "Tomate"),
)

@Composable
fun PerfilScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val colorBorde = Color(0xFF344E18)
    val colorTexto = colorBorde
    val colorIconos = colorBorde

    val user by userViewModel.user.collectAsState()
    val username = user?.username ?: "Invitado"

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val vm: MonitoreoViewModel = hiltViewModel()

    val loading by vm.loading.collectAsState()
    val repoError by vm.error.collectAsState()

    val openWeatherApiKey = "df02eb8f0cefd35cb63747c5c060dcc2"

    var place by remember { mutableStateOf<String?>(null) }
    var isRequesting by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val snapshotFlow = remember(place) {
        if (place.isNullOrBlank()) flowOf<WeatherSnapshot?>(null)
        else WeatherLocalStore.readSnapshot(context, place!!)
    }
    val snapshot by snapshotFlow.collectAsState(initial = null)

    var settingsExpanded by remember { mutableStateOf(false) } // Controls popup

    // --- Definición del Launcher ---
    // (Lo muevo aquí arriba para que esté disponible antes de ser usado en el LaunchedEffect)
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

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Perfil",
                        tint = colorIconos,
                        modifier = Modifier.size(24.dp)
                    )
                }

                TitleText(
                    text = ("Perfil"),
                    color = Color.Black,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                var settingsExpanded by remember { mutableStateOf(false) }

                // Settings Icon with dropdown menu
                IconButton(
                    onClick = { settingsExpanded = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = colorIconos,
                        modifier = Modifier.size(24.dp)
                    )
                }

                DropdownMenu(
                    expanded = settingsExpanded,
                    onDismissRequest = { settingsExpanded = false },
                    offset = DpOffset(x = (-100).dp, y = (-50).dp) // Ajusta estos valores
                ) {
                    DropdownMenuItem(
                        text = { Text("Log Out") },
                        onClick = {
                            settingsExpanded = false
                            userViewModel.logout()
                            navController.navigate("welcome") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Spacer(modifier = Modifier.height(30.dp))

            LaunchedEffect(Unit) {
                // 1. Mantenemos tu lógica original para cargar el usuario
                userViewModel.fetchCurrentUser()

                // 2. Comprobamos si ya tenemos permisos de ubicación
                val fineLocationGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                val coarseLocationGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                // Ponemos el estado de "solicitando" en true
                isRequesting = true

                if (fineLocationGranted || coarseLocationGranted) {
                    // 3. Si ya tenemos permisos, buscamos la ubicación directamente
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
                    // 4. Si NO tenemos permisos, lanzamos el diálogo para solicitarlos
                    permissionsLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            }
            // --- FIN DE LA MODIFICACIÓN ---

            UserCard(
                textColor = colorTexto,
                borderColor = colorBorde,
                nombreUsuario = username,
                ubicacion = place ?: "Ubicación no establecida",
                imagenUsuario = R.drawable.chiili
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TitleText(
                    text = ("Mis informes"),
                    color = Color.Black,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 50.dp),

                contentPadding = PaddingValues(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                items(sampleInformesRecientes) { informe ->
                    InfoCard(
                        informe = informe,
                        onMoreInformationClick = {
                            // TODO
                        }
                    )
                }
            }
        }
    }
}