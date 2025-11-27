package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.component.AppScaffold
import com.example.myapplication.model.RecetasViewModelFactory
import com.example.myapplication.viewmodel.ProfileViewModelFactory
import com.example.myapplication.session.UserSession
import com.example.myapplication.view.*
import com.example.myapplication.viewmodel.ProfileViewModel
import com.example.myapplication.viewmodel.RecetasViewModel

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN
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

        // REGISTER
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

        // HOME
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

        // 📌 LISTA DE RECETAS (MÍAS)
        composable("recetas") {
            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                MisRecetasScreen(
                    onRecipeClick = { /* abrir detalle */ },
                    onCreateNewRecipe = {
                        navController.navigate("crear_receta")
                    }
                )
            }
        }

        // 👤 PERFIL
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
        composable("crear_receta") {
            val context = LocalContext.current
            val session = UserSession(context)

            // Crear el ViewModel con la factory correcta
            val recetasViewModel: com.example.myapplication.viewmodel.RecetasViewModel = viewModel(
                factory = com.example.myapplication.model.RecetasViewModelFactory(context, session)
            )

            CrearRecetaScreen(
                onBack = { navController.popBackStack() },
                onCrear = { titulo, descripcion, tiempo, dificultad, imagenUri ->
                    android.util.Log.d("AppNavHost", "onCrear llamado desde NavHost -> titulo=$titulo")
                    // Llamar al ViewModel (asíncrono)
                    recetasViewModel.crearReceta(titulo, descripcion, tiempo, dificultad, imagenUri)
                    // Opcional: volver a la pantalla anterior inmediatamente
                    navController.popBackStack()
                }
            )
        }
    }
}
