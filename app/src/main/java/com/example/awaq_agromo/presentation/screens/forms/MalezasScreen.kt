package com.example.awaq_agromo.presentation.screens.forms

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.awaq_agromo.presentation.component.buttons.BackButton
import com.example.awaq_agromo.presentation.component.buttons.CheckBoxItem
import com.example.awaq_agromo.presentation.component.buttons.OptionItem
import com.example.awaq_agromo.presentation.component.buttons.PrimaryButton
import com.example.awaq_agromo.presentation.component.texts.BodyText
import com.example.awaq_agromo.presentation.component.texts.SubtitleText
import com.example.awaq_agromo.presentation.theme.Primary900
import androidx.core.net.toUri
import com.example.awaq_agromo.presentation.theme.AgromoTheme

@Composable
fun MalezaScreen(
    navController: NavController,
    onPhotoClick: () -> Unit,
    onBackPressed: () -> Unit,
    onDashboardClick: () -> Unit,
) {
    // Estados para las opciones Sí/No
    var tieneMalezas by remember { mutableStateOf(true) }

    // Estados para los tipos de maleza
    var hojaAncha by remember { mutableStateOf(false) }
    var rastreraTrepadora by remember { mutableStateOf(false) }
    var otraNoSe by remember { mutableStateOf(false) }
    var hojaAngosta by remember { mutableStateOf(false) }
    var arbustivaAlta by remember { mutableStateOf(false) }
    var ningunaTipo by remember { mutableStateOf(false) }

    // Estado para el porcentaje de área afectada
    var porcentajeArea by remember { mutableStateOf(0f) }

    // Estados para dónde se concentran
    var enLosBordes by remember { mutableStateOf(false) }
    var enZonasDispersas by remember { mutableStateOf(false) }
    var enElCentro by remember { mutableStateOf(false) }
    var enTodoElCultivo by remember { mutableStateOf(false) }

    // Estados para controles aplicados
    var controlManual by remember { mutableStateOf(false) }
    var controlQuimico by remember { mutableStateOf(false) }
    var controlCobertura by remember { mutableStateOf(false) }
    var noControl by remember { mutableStateOf(false) }

    // Estado para la URI de la imagen seleccionada
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher para seleccionar imagen de la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        // Esta lambda se ejecuta cuando el usuario selecciona una imagen
        uri?.let {
            selectedImageUri = it
            // Aquí puedes guardar la URI, mostrarla en un Image, etc.
            println("Imagen seleccionada: $it")
        }
    }

    // Observar cambios en la foto desde la cámara usando StateFlow
    val photoUriState = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<String?>("selectedPhotoUri", null)
        ?.collectAsState()

    AgromoTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(top = 56.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título principal
            item {
                Text(
                    text = "Malezas",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            item {
                Column {
                    Text(
                        text = "¿Qué tipo de maleza ves?",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OptionItem(
                            text = "Sí",
                            isSelected = tieneMalezas,
                            onOptionSelected = { tieneMalezas = true },
                            modifier = Modifier.padding(end = 24.dp)
                        )

                        OptionItem(
                            text = "No",
                            isSelected = !tieneMalezas,
                            onOptionSelected = { tieneMalezas = false }
                        )
                    }
                }
            }

            item {
                Text(
                    text = "¿Qué tipo de maleza ves?",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CheckBoxItem(
                            text = "Hoja ancha (tréboles, bejucos, verdolagas)",
                            isSelected = hojaAncha,
                            onOptionSelected = { hojaAncha = !hojaAncha }
                        )
                        CheckBoxItem(
                            text = "Rastrera o trepadora",
                            isSelected = rastreraTrepadora,
                            onOptionSelected = { rastreraTrepadora = !rastreraTrepadora }
                        )
                        CheckBoxItem(
                            text = "Otra / No sé",
                            isSelected = otraNoSe,
                            onOptionSelected = { otraNoSe = !otraNoSe }
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CheckBoxItem(
                            text = "Hoja angosta (gramíneas)",
                            isSelected = hojaAngosta,
                            onOptionSelected = { hojaAngosta = !hojaAngosta }
                        )
                        CheckBoxItem(
                            text = "Arbustiva o alta",
                            isSelected = arbustivaAlta,
                            onOptionSelected = { arbustivaAlta = !arbustivaAlta }
                        )
                        CheckBoxItem(
                            text = "Ninguna",
                            isSelected = ningunaTipo,
                            onOptionSelected = {
                                ningunaTipo = !ningunaTipo
                                // Si selecciona "Ninguna", deseleccionar los otros tipos
                                if (ningunaTipo) {
                                    hojaAncha = false
                                    rastreraTrepadora = false
                                    otraNoSe = false
                                    hojaAngosta = false
                                    arbustivaAlta = false
                                }
                            }
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Primary900, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        SubtitleText(
                            text = "Añada imágenes de malezas"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        BodyText(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Tome la fotografía de cerca y con buena luz. Asegúrese que se vea bien el área del cultivo donde aparecen las malezas"
                        )

                        // Efecto para manejar cuando llega una nueva foto
                        LaunchedEffect(photoUriState?.value) {
                            photoUriState?.value?.let { uriString ->
                                if (uriString.isNotEmpty()) {
                                    selectedImageUri = uriString.toUri()
                                    // Limpiar el estado después de usarlo
                                    navController.currentBackStackEntry
                                        ?.savedStateHandle
                                        ?.remove<String>("selectedPhotoUri")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Cámara",
                                tint = Primary900
                            )

                            PrimaryButton(
                                modifier = Modifier.weight(1f),
                                text = "Tomar Foto",
                                onClick = onPhotoClick
                            )
                        }

                        // Mostrar imagen seleccionada si existe
                        selectedImageUri?.let { uri ->
                            Spacer(modifier = Modifier.height(16.dp))
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "Imagen seleccionada",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = "Galería",
                                tint = Primary900
                            )

                            PrimaryButton(
                                modifier = Modifier.weight(1f),
                                text = "Seleccionar de la galería",
                                containerColor = Color(0xFFEFFFDE),
                                contentColor = Primary900,
                                onClick = {
                                    // Abrir selector de imágenes
                                    galleryLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Primary900, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Porcentaje del área afectada por malezas",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        BodyText(
                            text = "Desplace la barra deslizante al punto que de mayor semejanza con la situacion de su cultivo."
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Slider(
                            value = porcentajeArea,
                            onValueChange = { porcentajeArea = it },
                            valueRange = 0f..100f,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = "Porcentaje seleccionado: ${porcentajeArea.toInt()}%",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }

            // Quinta sección: Preguntas adicionales
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "¿Dónde se concentran?",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CheckBoxItem(
                            text = "En los bordes",
                            isSelected = enLosBordes,
                            onOptionSelected = { enLosBordes = !enLosBordes }
                        )
                        CheckBoxItem(
                            text = "En zonas dispersas",
                            isSelected = enZonasDispersas,
                            onOptionSelected = { enZonasDispersas = !enZonasDispersas }
                        )
                        CheckBoxItem(
                            text = "En el centro",
                            isSelected = enElCentro,
                            onOptionSelected = { enElCentro = !enElCentro }
                        )
                        CheckBoxItem(
                            text = "En todo el cultivo",
                            isSelected = enTodoElCultivo,
                            onOptionSelected = { enTodoElCultivo = !enTodoElCultivo }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "¿Ha aplicado algún tipo de control sobre las malezas?",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CheckBoxItem(
                            text = "Sí, manual (machete, azadón)",
                            isSelected = controlManual,
                            onOptionSelected = { controlManual = !controlManual }
                        )
                        CheckBoxItem(
                            text = "Sí, químico (herbicida)",
                            isSelected = controlQuimico,
                            onOptionSelected = { controlQuimico = !controlQuimico }
                        )
                        CheckBoxItem(
                            text = "Sí, cubriendo el suelo con cobertura orgánica",
                            isSelected = controlCobertura,
                            onOptionSelected = { controlCobertura = !controlCobertura }
                        )
                        CheckBoxItem(
                            text = "No se ha hecho control",
                            isSelected = noControl,
                            onOptionSelected = {
                                noControl = !noControl
                                // Si selecciona "No se ha hecho control", deseleccionar los otros controles
                                if (noControl) {
                                    controlManual = false
                                    controlQuimico = false
                                    controlCobertura = false
                                }
                            }
                        )
                    }
                }
            }

            // Sexta sección: Botones de navegación
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BackButton(
                        modifier = Modifier.weight(1f),
                        containerColor = Color(0xFFEFFFDE),
                        contentColor = Primary900,
                        onClick = onBackPressed
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        text = "Finalizar",
                        onClick = {
                            // Aquí puedes guardar todos los datos recopilados
                            // Por ejemplo, enviarlos a un ViewModel, base de datos, etc.
                            val datosMalezas = DatosMalezas(
                                tieneMalezas = tieneMalezas,
                                tiposMaleza = listOf(
                                    "hojaAncha" to hojaAncha,
                                    "rastreraTrepadora" to rastreraTrepadora,
                                    "otraNoSe" to otraNoSe,
                                    "hojaAngosta" to hojaAngosta,
                                    "arbustivaAlta" to arbustivaAlta,
                                    "ningunaTipo" to ningunaTipo
                                ),
                                porcentajeArea = porcentajeArea.toInt(),
                                concentracion = listOf(
                                    "enLosBordes" to enLosBordes,
                                    "enZonasDispersas" to enZonasDispersas,
                                    "enElCentro" to enElCentro,
                                    "enTodoElCultivo" to enTodoElCultivo
                                ),
                                controlesAplicados = listOf(
                                    "controlManual" to controlManual,
                                    "controlQuimico" to controlQuimico,
                                    "controlCobertura" to controlCobertura,
                                    "noControl" to noControl
                                )
                            )

                            // Procesar los datos como necesites
                            procesarDatosMalezas(datosMalezas)

                            // Navegar a la siguiente pantalla
                            onDashboardClick
                        }
                    )
                }
            }

            // Espacio extra para el BottomBar
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// Data class para guardar todos los datos de malezas
data class DatosMalezas(
    val tieneMalezas: Boolean,
    val tiposMaleza: List<Pair<String, Boolean>>,
    val porcentajeArea: Int,
    val concentracion: List<Pair<String, Boolean>>,
    val controlesAplicados: List<Pair<String, Boolean>>
)

// Función para procesar los datos (puedes adaptarla según tus necesidades)
fun procesarDatosMalezas(datos: DatosMalezas) {
    // Aquí puedes guardar en ViewModel, base de datos, enviar a API, etc.
    println("Datos de malezas guardados: $datos")
}