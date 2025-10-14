package com.example.awaq_agromo.presentation.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.presentation.component.buttons.PrimaryButton
import com.example.awaq_agromo.presentation.component.ui.ProgressBar
import com.example.awaq_agromo.presentation.component.buttons.SecondaryButton
import com.example.awaq_agromo.presentation.component.texts.SubtitleText
import com.example.awaq_agromo.presentation.component.texts.TitleText

@Preview(showSystemUi = true)
@Composable
fun Onboarding_Page_2Screen(
    onNextOnboardingPage3: () -> Unit = {}
){

    var progress by remember { mutableFloatStateOf(0.5f) }

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
            text = "Información de ubicación climatológica"
        )
        SubtitleText(
            text = "Obtenga la información actualizada del clima a través de la ubicación"
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    Color(0xFF829500),
                    shape = MaterialTheme.shapes.medium)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Ubicación"
                )

                Spacer(modifier = Modifier.height(15.dp))

                SubtitleText(
                    text = "Active los permisos de compartir ubicación",
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(
            text = "Activar",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        SecondaryButton(
            text = "Ahora no",
            onClick = onNextOnboardingPage3,
            textColor = Color(0xFF344E18)
        )
    }
}