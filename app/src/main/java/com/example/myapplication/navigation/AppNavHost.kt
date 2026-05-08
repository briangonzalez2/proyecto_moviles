package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.component.AppScaffold
import com.example.myapplication.viewmodel.ProfileViewModelFactory
import com.example.myapplication.view.*
import com.example.myapplication.viewmodel.ProfileViewModel
import com.example.myapplication.view.LoginScreen
import com.example.myapplication.session.UserSession

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // 🔐 LOGIN
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // 📝 REGISTER
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.navigate("login")
                }
            )
        }

        // 🏠 HOME
        composable("home") {
            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                MainMenuScreen(
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }
        }

        // 🍳 MIS RECETAS
        composable("recetas") {
            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                MisRecetasScreen(
                    onRecipeClick = { /* TODO */ },
                    onCreateNewRecipe = {
                        navController.navigate("crear_receta")
                    }
                )
            }
        }

        // 👤 PERFIL (puedes dejarlo así por ahora)
        composable("perfil") {

            val context = LocalContext.current
            val session = UserSession(context)

            val viewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModelFactory(context, session)
            )

            val imageUrl = viewModel.profileImageUrl.collectAsState().value

            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                ProfileScreen(
                    imageUrl = imageUrl,
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.navigate(it) },
                    onRecipeClick = {},
                    onImageSelected = { uri ->
                        viewModel.uploadProfileImage(uri)
                    }
                )
            }
        }

        // ⚙ CONFIG
        composable("config") {
            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                ConfigScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }

        // ➕ CREAR RECETA
        composable("crear_receta") {
            CrearRecetaScreen(
                onBack = { navController.popBackStack() },
                onCrear = { titulo, descripcion, tiempo, dificultad, imagenUri ->
                    // aquí luego conectamos Room
                    navController.popBackStack()
                }
            )
        }
    }
}
