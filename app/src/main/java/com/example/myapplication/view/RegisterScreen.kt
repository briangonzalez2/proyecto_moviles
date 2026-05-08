package com.example.myapplication.view

import android.app.Application
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.viewmodel.RegisterViewModel
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {

    val context = LocalContext.current

    val vm: RegisterViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            context.applicationContext as Application
        )
    )

    val state by vm.state.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // chef o user
    var role by remember { mutableStateOf("user") }

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
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Fast Cook!",
                fontSize = 60.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5E3B00)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text("Usuario", fontSize = 22.sp)

            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Usuario") }
            )

            Spacer(Modifier.height(20.dp))

            Text("Correo", fontSize = 22.sp)

            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Correo") }
            )

            Spacer(Modifier.height(20.dp))

            Text("Contraseña", fontSize = 22.sp)

            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(20.dp))

            Text("Confirmar contraseña", fontSize = 22.sp)

            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(20.dp))

            // ROLE
            Text("Tipo de usuario")

            Spacer(Modifier.height(8.dp))

            Row {

                Button(
                    onClick = { role = "user" }
                ) {
                    Text("Usuario")
                }

                Spacer(Modifier.width(12.dp))

                Button(
                    onClick = { role = "chef" }
                ) {
                    Text("Chef")
                }
            }

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {

                    if (password != confirmPassword) {
                        return@Button
                    }

                    vm.register(
                        username,
                        email,
                        password,
                        role
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6B4000),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Registrarse", fontSize = 20.sp)
            }

            Spacer(Modifier.height(16.dp))

            if (state.message != null) {

                Text(
                    text = state.message!!,
                    color = if (state.success)
                        Color(0xFF00A000)
                    else
                        Color.Red,
                    fontSize = 18.sp
                )

                if (state.success) {

                    LaunchedEffect(Unit) {
                        delay(1200)
                        onRegisterSuccess()
                    }
                }
            }

            TextButton(onClick = onBackToLogin) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}