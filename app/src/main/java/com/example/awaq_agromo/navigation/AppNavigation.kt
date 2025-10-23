package com.example.awaq_agromo.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.awaq_agromo.data.local.DataStoreManager
import com.example.awaq_agromo.presentation.component.ui.BottomBar
import com.example.awaq_agromo.presentation.component.ui.NavItem
import com.example.awaq_agromo.presentation.component.ui.items
import com.example.awaq_agromo.presentation.screens.camera.Analysis.AnalysisScreen
import com.example.awaq_agromo.presentation.screens.camera.PhotoScreen
import com.example.awaq_agromo.presentation.screens.dashboard.DashboardScreen
import com.example.awaq_agromo.presentation.screens.forms.ConditionsScreen
import com.example.awaq_agromo.presentation.screens.forms.DevelopmentScreen
import com.example.awaq_agromo.presentation.screens.forms.FoliageScreen
import com.example.awaq_agromo.presentation.screens.forms.HumedadScreen
import com.example.awaq_agromo.presentation.screens.forms.MalezaScreen
import com.example.awaq_agromo.presentation.screens.forms.MonitoreoScreen
import com.example.awaq_agromo.presentation.screens.forms.PhScreen
import com.example.awaq_agromo.presentation.screens.forms.PlagueScreen
import com.example.awaq_agromo.presentation.screens.forms.SicknessScreen
import com.example.awaq_agromo.presentation.screens.forms.StatesScreen
import com.example.awaq_agromo.presentation.screens.forms.TiraReactivaScreen
import com.example.awaq_agromo.presentation.screens.forms.VariedadScreen
import com.example.awaq_agromo.presentation.screens.login.LoginScreen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_1Screen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_2Screen
import com.example.awaq_agromo.presentation.screens.onboarding.Onboarding_Page_3Screen
import com.example.awaq_agromo.presentation.screens.perfil.PerfilScreen
import com.example.awaq_agromo.presentation.screens.registration.RegistrationScreen
import com.example.awaq_agromo.presentation.screens.welcome.WelcomeScreen
import com.example.awaq_agromo.presentation.viewmodel.LoginViewModel
import com.example.awaq_agromo.presentation.viewmodel.RegistrationViewModel
import com.example.awaq_agromo.presentation.viewmodel.UserViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    dataStoreManager: DataStoreManager,
) {
    val token by dataStoreManager.token.collectAsState(initial = null)
    val startDestination = if (!token.isNullOrEmpty()) "main_host" else "welcome"

    NavHost(navController = navController, startDestination = startDestination) {

        composable("welcome") {
            WelcomeScreen(
                onRegisterClick = { navController.navigate("registration") },
                onLoginClick = { navController.navigate("login") }
            )
        }

      /*  composable("login") {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onDashboardClick = {
                    navController.navigate("main_host") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }*/

        composable("registration") {
            val viewModel: RegistrationViewModel = hiltViewModel()
            RegistrationScreen(
                viewModel = viewModel,
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
                    navController.navigate("login?message=onboarding_success") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }

            )
        }

        composable(
            route = "login?message={message}",
            arguments = listOf(navArgument("message") { defaultValue = "" })
        ) { backStackEntry ->
            val message = backStackEntry.arguments?.getString("message")
            val viewModel: LoginViewModel = hiltViewModel()

            LoginScreen(
                viewModel = viewModel,


                onDashboardClick = { navController.navigate("main_host") {
                    popUpTo("welcome") {
                        inclusive = true }
                    } }, registrationMessage = if (message == "onboarding_success")
                    "Has sido registrado y has tomado el onboarding con éxito. Por favor, inicia sesión."
                else null
            )
        }

        composable("main_host") {
            MainScreenHost(navController = navController)
        }

        composable("photo_screen") {
            PhotoScreen(
                navController = navController,
                origin = "default"
            )
        }

        composable("malezas") {
            val context = LocalContext.current

            MalezaScreen(
                navController = navController,
                onPhotoClick = {
                    navController.navigate("photo_screen/formulario")
                },
                onDashboardClick = {
                    navController.navigate("main_host") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                onBackPressed = { navController.popBackStack() },
                context = context
            )
        }

        // RUTAS SEPARADAS PARA LA CÁMARA SEGÚN EL ORIGEN
        composable("photo_screen/main_host") {
            PhotoScreen(
                navController = navController,
                origin = "dashboard"
            )
        }

        composable("photo_screen/formulario") {
            PhotoScreen(
                navController = navController,
                origin = "formulario"
            )
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
    val bottomNavController = rememberNavController()
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
                val viewModel: UserViewModel = hiltViewModel()

                DashboardScreen(
                    navController = bottomNavController,
                    onPhotoClick = { navController.navigate("photo_screen") },
                    userViewModel = viewModel
                )
            }

            composable(NavItem.Monitoreo.route) {
                MonitoreoScreen(
                    onNext = {
                        bottomNavController.navigate("variedades")
                    }
                )
            }

            composable("variedades") { backStackEntry ->
                val userViewModel: UserViewModel = hiltViewModel() // get ViewModel via Hilt
                val userId = userViewModel.user.collectAsState().value?.id

                // Make sure crops are loaded when the screen enters
                LaunchedEffect(userId) {
                        userViewModel.loadCropsForUser(userId) // pass Int directly
                }

                VariedadScreen(
                    userViewModel = userViewModel,
                    onNext = {
                        bottomNavController.navigate("humedad")
                    }
                )
            }


            composable("humedad") { backStackEntry ->
                HumedadScreen(
                    onNext = { bottomNavController.navigate("ph")}
                )
            }

            composable("ph"){
                PhScreen(
                    onNext = { bottomNavController.navigate("conditions") },
                    onManualClick = { bottomNavController.navigate("manual") }
                )
            }

            composable("manual") {
                TiraReactivaScreen()
            }

            composable("conditions") {
                var selectedOption by remember { mutableStateOf("") }

                ConditionsScreen(
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it },
                    onNext = {
                        bottomNavController.navigate("desarrollo")
                    }
                )
            }

            composable("desarrollo"){
                DevelopmentScreen(
                    onNext = {
                        bottomNavController.navigate("foliage")
                    }
                )
            }

            composable("states"){
                StatesScreen(
                    onNext = {
                        bottomNavController.navigate("foliage")
                    }
                )
            }

            composable("foliage"){
                FoliageScreen(
                    onNext = {
                        bottomNavController.navigate("plagas")
                    }
                )
            }

            composable("plagas"){
                PlagueScreen(
                    onNext = {
                        bottomNavController.navigate("enfermedades")
                    }
                )
            }

            composable("enfermedades"){
                SicknessScreen(
                    onNext = {
                        bottomNavController.navigate("malezas")
                    }
                )
            }

            composable("malezas") {
                val context = LocalContext.current

                MalezaScreen(
                    navController = navController,
                    onPhotoClick = {
                        navController.navigate("photo_screen/formulario")
                    },
                    onDashboardClick = {
                        navController.navigate("main_host") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    },
                    onBackPressed = { navController.popBackStack() },
                    context = context
                )
            }

            composable(NavItem.Comunidad.route) {
                Text("Placeholder de Comunidad Screen", modifier = Modifier.fillMaxSize())
            }

            composable(NavItem.Perfil.route) {
                PerfilScreen(navController = navController)
            }
        }
    }
}

