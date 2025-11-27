package com.example.myapplication.view

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.RegisterViewModel
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {

    val vm: RegisterViewModel = viewModel()
    val state by vm.state.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFB948)) // Color amarillo similar
    )

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

        Spacer(modifier = Modifier.height(60.dp))

        Text("Usuario", fontSize = 28.sp, color = Color(0xFF5E3B00))
        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(10.dp)),
            placeholder = { Text("Usuario", fontSize = 20.sp) },
        )

        Spacer(Modifier.height(30.dp))

        Text("Contraseña", fontSize = 28.sp, color = Color(0xFF5E3B00))
        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(10.dp)),
            placeholder = { Text("Contraseña", fontSize = 20.sp) },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(30.dp))

        Text("Confirme contraseña", fontSize = 28.sp, color = Color(0xFF5E3B00))
        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(10.dp)),
            placeholder = { Text("Confirme contraseña", fontSize = 20.sp) },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(60.dp))

        Button(
            onClick = {
                if (password == confirmPassword && username.isNotEmpty()) {
                    vm.register(username, password)
                }
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

        state?.let { result ->

            if (result.success) {

                // Muestra mensaje verde
                Text(
                    text = result.message ?: "Usuario registrado correctamente",
                    color = Color(0xFF00A000),
                    fontSize = 18.sp
                )

                LaunchedEffect(Unit) {
                    delay(1200)
                    onRegisterSuccess()
                }

            } else {
                Text(
                    text = result.message ?: "Error al registrar",
                    color = Color.Red,
                    fontSize = 18.sp
                )
            }
        }

        TextButton(onClick = onBackToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}
