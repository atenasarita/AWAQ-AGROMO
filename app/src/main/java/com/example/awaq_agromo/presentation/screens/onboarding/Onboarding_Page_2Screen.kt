package com.example.awaq_agromo.presentation.screens.onboarding

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.awaq_agromo.presentation.component.buttons.PrimaryButton
import com.example.awaq_agromo.presentation.component.ui.ProgressBar
import com.example.awaq_agromo.presentation.component.buttons.SecondaryButton
import com.example.awaq_agromo.presentation.component.texts.SubtitleText
import com.example.awaq_agromo.presentation.component.texts.TitleText
import com.example.awaq_agromo.presentation.theme.AgromoTheme

@Preview(showSystemUi = true)
@Composable
fun Onboarding_Page_2Screen(
    onNextOnboardingPage3: () -> Unit = {}
) {
    val context = LocalContext.current
    var progress by remember { mutableFloatStateOf(0.5f) }
    var locationPermissionGranted by remember { mutableStateOf(false) }

    // Launcer para solicitar el permiso de ubicacion
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

        locationPermissionGranted = fineLocationGranted

        if (locationPermissionGranted) {
            println("Permiso de ubicacion concedido")
            onNextOnboardingPage3()
        } else {
            println("Permiso de ubicacion denegado")
        }
    }

    AgromoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProgressBar(progress = progress)

            Spacer(modifier = Modifier.height(20.dp))

            TitleText(
                text = "Información de ubicación climatológica",
                fontSize = 25.sp
            )
            SubtitleText(
                text = "Obtenga la información actualizada del clima a través de la ubicación",
                textAlign = androidx.compose.ui.text.style.TextAlign.Start
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .size(180.dp)
                    .background(
                        Color(0xFF829500),
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(shape = CircleShape)
                            .background(Color.White)
                            .padding(15.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Ubicación",
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                    SubtitleText(
                        text = "Active los permisos de compartir ubicación",
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 25.dp, end = 25.dp, top = 15.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            PrimaryButton(
                text = "Activar",
                onClick = {
                    val hasPermission = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                    if (hasPermission) {
                        locationPermissionGranted = true
                        onNextOnboardingPage3()
                    } else {
                        locationPermissionLauncher.launch(
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecondaryButton(
                text = "Ahora no",
                onClick = onNextOnboardingPage3,
                textColor = Color(0xFF344E18)
            )
        }
    }
}