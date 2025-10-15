package com.example.awaq_agromo.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.awaq_agromo.presentation.component.ui.BottomBar
import com.example.awaq_agromo.presentation.component.ui.NavItem
import com.example.awaq_agromo.presentation.component.ui.items
import com.example.awaq_agromo.presentation.screens.camera.PhotoScreen
import com.example.awaq_agromo.presentation.screens.dashboard.DashboardScreen
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
                onRegisterClick = { navController.navigate("registration") },
                onLoginClick = { navController.navigate("login") }
            )
        }

        composable("login") {
            LoginScreen(
                onDashboardClick = {
                    navController.navigate("main_host") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }

        composable("registration") {
            RegistrationScreen(
                onOnboardingClick = { navController.navigate("Onboarding_1") }
            )
        }

        composable("Onboarding_1") {
            Onboarding_Page_1Screen(
                onNextOnboardingPage2 = { navController.navigate("Onboarding_2") }
            )
        }

        composable("Onboarding_2") {
            Onboarding_Page_2Screen(
                onNextOnboardingPage3 = { navController.navigate("Onboarding_3") }
            )
        }

        composable("Onboarding_3") {
            Onboarding_Page_3Screen(
                onDashboardClick = {
                    navController.navigate("main_host") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }

        composable("main_host") {
            MainScreenHost(navController = navController)
        }

       // composable("photo_screen") {
         //   PhotoScreen()
       // }
    }
}


@Composable
fun MainScreenHost(navController: NavHostController) {
    val bottomNavController = rememberNavController() // Inner controller!

    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavItem.Inicio.route
    val bottomBarRoutes = items.map { it.route }

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomBar(
                    selectedRoute = currentRoute,
                    onNavigate = { newRoute ->
                        bottomNavController.navigate(newRoute) {
                            popUpTo(bottomNavController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = NavItem.Inicio.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(NavItem.Inicio.route) {
                DashboardScreen(navController = bottomNavController) // Or your actual dashboard
            }
            composable(NavItem.Monitoreo.route) {
                Text("Placeholder de Monitoreo Screen", modifier = Modifier.fillMaxSize())
            }
            composable(NavItem.Comunidad.route) {
                Text("Placeholder de Comunidad Screen", modifier = Modifier.fillMaxSize())
            }
            composable(NavItem.Perfil.route) {
                PerfilScreen()
            }
        }
    }
}
