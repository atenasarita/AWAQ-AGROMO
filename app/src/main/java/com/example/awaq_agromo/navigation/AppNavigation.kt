package com.example.awaq_agromo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.awaq_agromo.presentation.screens.forms.HumedadScreen
import com.example.awaq_agromo.presentation.screens.forms.MonitoreoScreen
import com.example.awaq_agromo.presentation.screens.forms.PhScreen
import com.example.awaq_agromo.presentation.screens.forms.VariedadScreen
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
            LoginScreen(onDashboardClick = { navController.navigate("monitoreo") })
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

        composable("monitoreo") {
            MonitoreoScreen( onNext = {navController.navigate("variedad")} )
        }

        composable("variedad") {
            VariedadScreen( onNext = {navController.navigate("humedad")} )
        }

        composable("humedad") {
            HumedadScreen( onNext = {navController.navigate("pH")} )
        }

        composable("pH") {
            PhScreen()
        }

        composable("perfil") {
            PerfilScreen()
        }
    }
}