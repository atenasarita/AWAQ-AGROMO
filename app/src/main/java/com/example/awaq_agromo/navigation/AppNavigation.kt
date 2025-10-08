package com.example.awaq_agromo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.awaq_agromo.presentation.screens.camera.PhotoScreen
import com.example.awaq_agromo.presentation.screens.login.LoginScreen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_1Screen
import com.example.awaq_agromo.presentation.screens.perfil.PerfilScreen
import com.example.awaq_agromo.presentation.screens.registration.RegistrationScreen
import com.example.awaq_agromo.presentation.screens.welcome.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {

        composable("welcome") {
            WelcomeScreen(
                onRegisterClick = {
                    navController.navigate("registration")
                },
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }
        composable("login") {
            LoginScreen(onLogin = { navController.navigate("cultivo") })
        }
        composable("registration") {
            RegistrationScreen()
        }
        composable("Onboarding") {
            Onboarding_Page_1Screen()
        }
        composable("cultivo") {
            IdCultivoScreen(navController)
        }
        composable("clima") {
            ClimaScreen(navController)
        }
        composable("photo") {
            PhotoScreen(navController)
        }
        composable("perfil") {
            PerfilScreen()
        }
    }
}