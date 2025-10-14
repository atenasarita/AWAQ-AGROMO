package com.example.awaq_agromo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.awaq_agromo.presentation.screens.camera.PhotoScreen
import com.example.awaq_agromo.presentation.screens.login.LoginScreen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_1Screen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_2Screen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_3Screen
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
            LoginScreen(onDashboardClick = { navController.navigate("perfil") })
        }
        composable("registration") {
            RegistrationScreen(
                onOnboardingClick = { navController.navigate("Onboarding_1") }
            )
        }
        composable("Onboarding_1") {
            Onboarding_Page_1Screen(
                onNextOnboardingPage2 = {navController.navigate("Onboarding_2")}
            )
        }
        composable("Onboarding_2") {
            Onboarding_Page_2Screen(
                onNextOnboardingPage3 = {navController.navigate("Onboarding_3")}
            )
        }
        composable("Onboarding_3") {
            Onboarding_Page_3Screen(
                onDashboardClick = {navController.navigate("perfil")}
            )
        }
        composable("dashboard") {
            /*Dashboard(
                onPerfilClick = {navController.navigate("perfil")}
            )*/
        }
        composable("perfil") {
            PerfilScreen()
        }
    }
}