package com.example.awaq_agromo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLogin = { navController.navigate("cultivo") })
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
    }
}
