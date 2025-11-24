package com.example.myapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5E3B00)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Usuario
        Text("Usuario", fontSize = 28.sp, color = Color(0xFF5E3B00))
        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Usuario", fontSize = 20.sp) },
        )

        Spacer(Modifier.height(30.dp))

        // Contraseña
        Text("Contraseña", fontSize = 28.sp, color = Color(0xFF5E3B00))
        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Contraseña", fontSize = 20.sp) },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(30.dp))

        // Confirmar contraseña
        Text("Confirme contraseña", fontSize = 28.sp, color = Color(0xFF5E3B00))
        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Confirme contraseña", fontSize = 20.sp) },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(60.dp))

        Button(
            onClick = {
                if (password == confirmPassword && username.isNotEmpty()) {
                    onRegisterSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6B4000),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)  // <- SOLUCION
        ) {
            Text("Registrarse", fontSize = 20.sp)
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onBackToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(
        onRegisterSuccess = {},
        onBackToLogin = {}
    )
}
