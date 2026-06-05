package com.example.myapplication.view

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.RegisterViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.myapplication.model.RegisterState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {

    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    // Evita errores en Preview
    val vm: RegisterViewModel? = if (isPreview) {
        null
    } else {
        viewModel(
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
                context.applicationContext as Application
            )
        )
    }

    val state = if (isPreview) {
        remember { mutableStateOf(RegisterState()) }.value
    } else {
        vm!!.state.collectAsState().value
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }


    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // user o chef
    var role by remember { mutableStateOf("user") }

    LaunchedEffect(state.success) {

        if (state.success && !isPreview) {

            snackbarHostState.showSnackbar(
                message = state.message ?: "Usuario registrado correctamente"
            )

            delay(800)

            onRegisterSuccess()
        }
    }

    LaunchedEffect(state.message) {

        if (!state.success) {

            state.message?.let { msg ->

                snackbarHostState.showSnackbar(
                    message = msg
                )
            }
        }
    }

    MyApplicationTheme(dynamicColor = false) {

        Scaffold(

            containerColor = MaterialTheme.colorScheme.primary,

            snackbarHost = {

                SnackbarHost(
                    hostState = snackbarHostState
                )
            }

        ) { innerPadding ->

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState),
                color = MaterialTheme.colorScheme.primary
            ) {

                Box(
                    modifier = Modifier.fillMaxSize()
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
                            .padding(horizontal = 20.dp)
                            .systemBarsPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        Spacer(modifier = Modifier.height(80.dp))

                        Text(
                            text = "Fast Cook!",
                            fontSize = 60.sp,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        val textFieldColors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            cursorColor = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = "Usuario",
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(Modifier.height(6.dp))

                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    "Ejemplo: Juanito",
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            },
                            colors = textFieldColors
                        )

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = "Correo",
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(Modifier.height(6.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    "Ejemplo: juanito@gmail.com",
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            },
                            colors = textFieldColors
                        )

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = "Contraseña",
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(Modifier.height(6.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    "Debe tener al menos 8 caracteres",
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            colors = textFieldColors
                        )

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = "Confirmar contraseña",
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(Modifier.height(6.dp))

                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    "Debe ser igual a la contraseña",
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            colors = textFieldColors
                        )

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = "Tipo de usuario",
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(Modifier.height(8.dp))

                        Row {

                            Button(
                                onClick = { role = "user" },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        if (role == "user")
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Usuario")
                            }

                            Spacer(Modifier.width(12.dp))

                            Button(
                                onClick = { role = "chef" },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        if (role == "chef")
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Chef")
                            }
                        }

                        Spacer(Modifier.height(40.dp))

                        Button(
                            onClick = {

                                if (password != confirmPassword) {

                                    scope.launch {

                                        snackbarHostState.showSnackbar(
                                            message = "Las contraseñas no coinciden"
                                        )
                                    }

                                    return@Button
                                }

                                vm?.register(
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
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "Registrarse",
                                fontSize = 20.sp
                            )
                        }

                        Spacer(Modifier.height(16.dp))


                        if (isPreview) {

                            LaunchedEffect(Unit) {
                                delay(1200)
                                onRegisterSuccess()
                            }
                        }
                    }
                    TextButton(
                        onClick = onBackToLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {
                        Text(
                            text = "¿Ya tienes cuenta? Inicia sesión",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {

    RegisterScreen(
        onRegisterSuccess = {},
        onBackToLogin = {}
    )
}