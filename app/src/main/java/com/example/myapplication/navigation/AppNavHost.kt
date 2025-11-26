package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.component.AppScaffold
import com.example.myapplication.model.ProfileViewModelFactory
import com.example.myapplication.session.UserSession
import com.example.myapplication.view.*
import com.example.myapplication.viewmodel.ProfileViewModel

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // login
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

        // register
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

        // home sidebar
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

        // receta
        composable("recetas") {
            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                MisRecetasScreen(
                    onRecipeClick = { /* detalle */ }
                )
            }
        }

        // perfil
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

        //configuracion
        composable("config") {
            AppScaffold(
                onNavigate = { route -> navController.navigate(route) }
            ) {
                ConfigScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
