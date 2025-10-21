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
import androidx.compose.ui.unit.dp
import com.example.awaq_agromo.presentation.component.ui.AgromoLogo
import com.example.awaq_agromo.presentation.component.texts.AgromoPasswordField
import com.example.awaq_agromo.presentation.component.buttons.AgromoPrimaryButton
import com.example.awaq_agromo.presentation.component.buttons.AgromoPrivacyCheckbox
import com.example.awaq_agromo.presentation.component.buttons.AgromoSecondaryButton
import com.example.awaq_agromo.presentation.component.texts.AgromoTextField
import com.example.awaq_agromo.presentation.theme.AgromoTheme
import com.example.awaq_agromo.presentation.theme.Primary50
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.example.awaq_agromo.presentation.viewmodel.RegistrationState
import com.example.awaq_agromo.presentation.viewmodel.RegistrationViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    onOnboardingClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var privacyChecked by remember { mutableStateOf(false) }

    val isFormValid = name.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            password == confirmPassword &&
            privacyChecked

    val registrationState by viewModel.state.collectAsState()

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

                AgromoTextField("Nombre y Apellido", name, onValueChange = { name = it })
                AgromoTextField(
                    "Correo Electrónico",
                    email,
                    onValueChange = { email = it },
                    keyboardType = KeyboardType.Email
                )
                AgromoPasswordField("Contraseña", password, onValueChange = { password = it })
                AgromoPasswordField(
                    "Confirmar Contraseña",
                    confirmPassword,
                    onValueChange = { confirmPassword = it })

                AgromoPrivacyCheckbox(
                    checked = privacyChecked,
                    onCheckedChange = { privacyChecked = it },
                    onLinkClick = { println("Ir a Política de Privacidad") }
                )

                Spacer(Modifier.height(16.dp))

                if (isFormValid) {
                    AgromoPrimaryButton(
                        text = "REGISTRARME",
                        onClick = { viewModel.registerUser(name, email, password) }
                    )
                } else {
                    AgromoSecondaryButton(
                        text = "REGISTRARME",
                        onClick = { println("Formulario no válido") }
                    )
                }

                when (registrationState) {
                    is RegistrationState.Loading -> {
                        Text("Registrando usuario...", color = Color.Gray)
                    }

                    is RegistrationState.Error -> {
                        Text(
                            text = (registrationState as RegistrationState.Error).message,
                            color = Color.Red
                        )
                    }

                    is RegistrationState.Success -> {
                        onOnboardingClick()
                    }

                    else -> {}
                }
            }
        }
    }
}