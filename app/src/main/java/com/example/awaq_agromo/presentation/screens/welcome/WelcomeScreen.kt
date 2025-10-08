package com.example.awaq_agromo.presentation.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agromo_ai.ui.components.AgromoLogo
import com.example.awaq_agromo.components.PrimaryButton
import com.example.awaq_agromo.components.SecondaryButton
import com.example.awaq_agromo.components.SubtitleText

@Composable
fun WelcomeScreen(
    onRegisterClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            AgromoLogo()

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        Color(0xFF829500),
                        shape = MaterialTheme.shapes.medium)
            ) {
                SubtitleText(
                    text = "Monitoree sus cultivos y genere\ninformes integrales",
                    modifier = Modifier.padding(10.dp),
                    color = Color.White

                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Botones
            PrimaryButton(
                text = "Quiero registrarme",
                onClick = onRegisterClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecondaryButton(
                text = "Ya tengo cuenta",
                onClick = onLoginClick,
                textColor = Color(0xFF344E18)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}