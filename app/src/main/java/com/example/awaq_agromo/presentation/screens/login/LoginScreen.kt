package com.example.awaq_agromo.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.agromo_ai.ui.components.AgromoLogo
import com.example.awaq_agromo.components.AgromoPasswordField
import com.example.awaq_agromo.components.AgromoPrimaryButton
import com.example.awaq_agromo.components.AgromoSecondaryButton
import com.example.awaq_agromo.components.AgromoTextField
import com.example.awaq_agromo.ui.theme.Primary50
import com.example.awaq_agromo.ui.theme.AgromoTheme

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isFormValid =
        email.isNotBlank() &&
                password.isNotBlank()

    AgromoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Primary50)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AgromoLogo(modifier = Modifier.padding(bottom = 24.dp))

                AgromoTextField(
                    label = "Correo Electrónico",
                    value = email,
                    onValueChange = { email = it },
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                AgromoPasswordField(
                    label = "Contraseña",
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (isFormValid) {
                    AgromoPrimaryButton(
                        text = "INICIAR SESIÓN",
                        onClick = {
                            println("Registrando usuario...")
                        }
                    )
                } else {
                    AgromoSecondaryButton(
                        text = "INICIAR SESIÓN",
                        onClick = {  }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("¿No tienes una cuenta? Crear cuenta", modifier = Modifier.padding(vertical = 8.dp))
            }

        }
    }
}