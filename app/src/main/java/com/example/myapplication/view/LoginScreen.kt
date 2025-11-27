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
import androidx.compose.ui.graphics.fromColorLong
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Orange80
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

    var email by remember { mutableStateOf("") }
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

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
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

        Button(
            onClick = {
                vm.login(email, pass, session)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }

            Text("Usuario", color = Color.DarkGray, fontSize = 16.sp)
            Spacer(Modifier.height(6.dp))

            TextField(
                value = usuario,
                onValueChange = { usuario = it },
                placeholder = { Text("Usuario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(16.dp))


            Text("Contraseña", color = Color.DarkGray, fontSize = 16.sp)
            Spacer(Modifier.height(6.dp))

            TextField(
                value = pass,
                onValueChange = { pass = it },
                placeholder = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B5A06) // Marrón como en la imagen
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Login", color = Color.White, fontSize = 18.sp)
            }

            Spacer(Modifier.height(16.dp))

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
        // ⚠ MENSAJES (éxito/error)
        state.message?.let { msg ->
            Text(
                text = msg,
                color = if (state.success) Color(0xFF008F39) else Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // ⚠ SI LOGIN ES EXITOSO → NAVIGAR AUTOMÁTICAMENTE
        if (state.success) {
            LaunchedEffect(true) {
                onLoginSuccess()
            }
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}
