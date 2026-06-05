package com.example.myapplication.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.session.UserSession

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {

    val vm: LoginViewModel = viewModel()
    val state by vm.state.collectAsState()

    val context = LocalContext.current
    val session = remember { UserSession(context) }

    // VARIABLES CORRECTAS
    var email by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }   // ← ESTA FALTABA
    var pass by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFB948))
    ) {

        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.15f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Fast Cook!",
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // ------------------ CORREO ------------------
            Text("Correo", color = Color.DarkGray, fontSize = 16.sp)
            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // ------------------ USUARIO ------------------
            Text("Usuario", color = Color.DarkGray, fontSize = 16.sp)
            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                placeholder = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // ------------------ CONTRASEÑA ------------------
            Text("Contraseña", color = Color.DarkGray, fontSize = 16.sp)
            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                placeholder = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // ------------------ BOTÓN LOGIN ------------------
            Button(
                onClick = {
                    vm.login(email, pass, session)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B5A06)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ingresar", color = Color.White, fontSize = 18.sp)
            }

            Spacer(Modifier.height(16.dp))

            // ------------------ REGISTER ------------------
            OutlinedButton(
                onClick = onNavigateToRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF8B5A06)
                ),
                border = BorderStroke(2.dp, Color(0xFF8B5A06))
            ) {
                Text("Register", fontSize = 16.sp)
            }

            // ------------------ MENSAJES ------------------
            state.message?.let { msg ->
                Text(
                    text = msg,
                    color = if (state.success) Color(0xFF008F39) else Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // ------------------ REDIRECCIÓN ------------------
            if (state.success) {
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}
