package com.example.awaq_agromo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.awaq_agromo.navigation.IdCultivoScreen
import com.example.awaq_agromo.navigation.LoginScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(onLogin = { navController.navigate("cultivo") }) }
        composable("cultivo") { IdCultivoScreen() }
    }
}