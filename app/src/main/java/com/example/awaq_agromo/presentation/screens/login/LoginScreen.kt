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
import com.example.awaq_agromo.presentation.component.ui.AgromoLogo
import com.example.awaq_agromo.presentation.component.texts.AgromoPasswordField
import com.example.awaq_agromo.presentation.component.buttons.AgromoPrimaryButton
import com.example.awaq_agromo.presentation.component.texts.AgromoTextField
import com.example.awaq_agromo.presentation.theme.Primary50
import com.example.awaq_agromo.presentation.theme.AgromoTheme
import com.example.awaq_agromo.presentation.viewmodel.LoginState

import com.example.awaq_agromo.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onDashboardClick: () -> Unit,
    viewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    val isFormValid = email.isNotBlank() && password.isNotBlank()

    // React to login success
    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            onDashboardClick()
        }
    }

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

                when (state) {
                    is LoginState.Loading -> CircularProgressIndicator()
                    is LoginState.Error -> Text(
                        text = (state as LoginState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                    else -> {}
                }

                AgromoPrimaryButton(
                    text = "INICIAR SESIÓN",
                    onClick = {
                        if (isFormValid) viewModel.loginUser(email, password)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "¿No tienes una cuenta? Crear cuenta",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}