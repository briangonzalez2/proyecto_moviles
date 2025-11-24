package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.component.AppScaffold
import com.example.myapplication.view.*

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // -----------------------------
        //  RUTAS SIN SIDEBAR/TOPBAR
        // -----------------------------
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }   // elimina login del back stack
                    }
                }

                , onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("home") }
                , onBackToLogin = { navController.navigate("login") }
            )
        }

        // -----------------------------
        //  RUTAS CON SIDEBAR/TOPBAR
        // -----------------------------
        composable("home") {
            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                MainMenuScreen(onNavigate = { route -> navController.navigate(route) })
            }
        }

        composable("recetas") {
            AppScaffold(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            ) {
                MisRecetasScreen(
                    onRecipeClick = {
                        // Si tienes una receta detallada:
                        // navController.navigate("receta_detalle")
                    }
                )
            }
        }

        // CONFIG (pantalla configuración)
        composable("config") {
            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                ConfigScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("perfil") {
            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                ProfileScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { route -> navController.navigate(route) },
                    onRecipeClick = { /* manejar selección */ }
                )
            }
        }
    }
}
