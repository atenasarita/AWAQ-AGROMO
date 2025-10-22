package com.example.awaq_agromo.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.awaq_agromo.presentation.component.ui.BottomBar
import com.example.awaq_agromo.presentation.component.ui.NavItem
import com.example.awaq_agromo.presentation.component.ui.items
import com.example.awaq_agromo.presentation.screens.camera.Analysis.AnalysisScreen
import com.example.awaq_agromo.presentation.screens.camera.PhotoScreen
import com.example.awaq_agromo.presentation.screens.dashboard.DashboardScreen
import com.example.awaq_agromo.presentation.screens.login.LoginScreen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_1Screen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_2Screen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_3Screen
import com.example.awaq_agromo.presentation.screens.perfil.PerfilScreen
import com.example.awaq_agromo.presentation.screens.registration.RegistrationScreen
import com.example.awaq_agromo.presentation.screens.welcome.WelcomeScreen
import com.example.awaq_agromo.presentation.screens.forms.ConditionsScreen
import com.example.awaq_agromo.presentation.screens.forms.DevelopmentScreen
import com.example.awaq_agromo.presentation.screens.forms.FoliageScreen
import com.example.awaq_agromo.presentation.screens.forms.PlagueScreen
import com.example.awaq_agromo.presentation.screens.forms.SicknessScreen
import com.example.awaq_agromo.presentation.screens.forms.StatesScreen

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


        composable("development") {
            DevelopmentScreen(onNext = {navController.navigate("states")})
        }

        composable("states") {
            StatesScreen(onNext = {navController.navigate("foliage")})
        }

        composable("foliage") {
            FoliageScreen(onNext = {navController.navigate("plague")})
        }

        composable("plague") {
            PlagueScreen(onNext = {navController.navigate("sickness")})
        }

        composable("sickness") {
            SicknessScreen()
        }
        composable("conditions") {
            ConditionsScreen(onNext = {navController.navigate("development")})
        }

        composable("main_host") {
            MainScreenHost(navController = navController)
        }

        composable("photo_screen") {
            PhotoScreen(navController = navController)
        }

        composable(
            route = "analysis/{imageUri}",
            arguments = listOf(
                navArgument("imageUri") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val encodedUri = backStackEntry.arguments?.getString("imageUri")
            val decodedUri = encodedUri?.let { Uri.decode(it) }
            AnalysisScreen(
                navController = navController,
                imageUri = decodedUri
            )
        }
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
                DashboardScreen(
                    navController = bottomNavController,
                    onPhotoClick = { navController.navigate("photo_screen") }
                    ) // Or your actual dashboard

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
