package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.component.AppScaffold
import com.example.myapplication.session.UserSession
import com.example.myapplication.view.*
import com.example.myapplication.viewmodel.ProfileViewModel

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

                        popUpTo("login") {
                            inclusive = true
                        }
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

                        popUpTo("register") {
                            inclusive = true
                        }
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
                onNavigate = { route ->
                    navController.navigate(route)
                }
            ) {

                MainMenuScreen(

                    onNavigate = { route ->
                        navController.navigate(route)
                    },

                    onLogout = {

                        navController.navigate("login") {

                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        // 🍳 MIS RECETAS
        composable("recetas") {

            AppScaffold(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            ) {

                MisRecetasScreen(

                    onRecipeClick = { recetaId ->

                        navController.navigate(
                            "receta/$recetaId"
                        )
                    },

                    onCreateNewRecipe = {

                        navController.navigate(
                            "crear_receta"
                        )
                    }
                )
            }
        }

        // 📖 DETALLE RECETA
        composable(
            route = "receta/{id}",

            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val recetaId =
                backStackEntry.arguments?.getInt("id") ?: 0

            AppScaffold(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            ) {

                RecetasScreen(
                    recetaId = recetaId
                )
            }
        }

        // 👤 PERFIL
        composable("perfil") {

            AppScaffold(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            ) {

                ProfileScreen(

                    onBack = {
                        navController.popBackStack()
                    },

                    onNavigate = {
                        navController.navigate(it)
                    },

                    onRecipeClick = { recetaId ->

                        navController.navigate(
                            "receta/$recetaId"
                        )
                    }
                )
            }
        }

        // ⚙ CONFIG
        composable("config") {

            AppScaffold(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            ) {

                ConfigScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // ➕ CREAR RECETA
        composable("crear_receta") {
            AppScaffold(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            ) {
                CrearRecetaScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}