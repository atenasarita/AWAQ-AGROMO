package com.example.awaq_agromo.presentation.screens.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agromo_ai.ui.components.AgromoLogo
import com.example.awaq_agromo.components.AgromoPasswordField
import com.example.awaq_agromo.components.AgromoPrimaryButton
import com.example.awaq_agromo.components.AgromoPrivacyCheckbox
import com.example.awaq_agromo.components.AgromoSecondaryButton
import com.example.awaq_agromo.components.AgromoTextField
import com.example.awaq_agromo.ui.theme.AgromoTheme
import com.example.awaq_agromo.ui.theme.Primary50

@Composable
fun RegistrationScreen() {
    // 1. Estados de la pantalla
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var privacyChecked by remember { mutableStateOf(false) }

    // Lógica simple de validación (solo verifica que todos los campos requeridos estén llenos)
    val isFormValid = name.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            password == confirmPassword &&
            privacyChecked

    AgromoTheme{
        // Fondo de la pantalla con el color PrincipalPrimary (Verde Oscuro)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Primary50) // Usando el color PrincipalNeutral para un fondo sutil
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Contenido de la tarjeta con scroll
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- Header ---
                AgromoLogo(modifier = Modifier.padding(bottom = 24.dp))

                // --- Campos de Formulario ---
                AgromoTextField(
                    label = "Nombre y Apellido",
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

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

                AgromoPasswordField(
                    label = "Confirmar Contraseña",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // --- Checkbox de Privacidad ---
                AgromoPrivacyCheckbox(
                    checked = privacyChecked,
                    onCheckedChange = { privacyChecked = it },
                    onLinkClick = {
                        // Aquí se implementaría la navegación a la Política de Privacidad
                        println("Navegando a Política de Privacidad...")
                    },
                    modifier = Modifier.padding(bottom = 32.dp)
                )


                // --- Botón de Registro Dinámico ---
                if (isFormValid) {
                    AgromoPrimaryButton(
                        text = "REGISTRARME",
                        onClick = {
                            // Lógica de registro aquí
                            println("Registrando usuario...")
                        }
                    )
                } else {
                    // Muestra el botón sutil cuando el formulario NO es válido (Diseño original)
                    AgromoSecondaryButton(
                        text = "REGISTRARME",
                        onClick = { /* No hacer nada o mostrar error */ }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("¿Ya tienes una cuenta? Iniciar sesión", modifier = Modifier.padding(vertical = 8.dp))
            }

        }
    }
}


@Preview
@Composable
fun RegistrationScreenPreview(){
    RegistrationScreen()
}