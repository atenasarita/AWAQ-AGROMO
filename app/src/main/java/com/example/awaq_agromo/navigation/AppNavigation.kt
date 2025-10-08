package com.example.awaq_agromo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.awaq_agromo.presentation.screens.camera.PhotoScreen
import com.example.awaq_agromo.presentation.screens.login.LoginScreen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_1Screen
import com.example.awaq_agromo.presentation.screens.perfil.PerfilScreen
import com.example.awaq_agromo.presentation.screens.welcome.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcom") {

        composable("welcom") {
            WelcomeScreen(
                onRegisterClick = {
                    // Navegar a pantalla de registro
                },
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }
        composable("login") {
            LoginScreen(onLogin = { navController.navigate("cultivo") })
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