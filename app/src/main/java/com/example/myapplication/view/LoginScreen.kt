package com.example.myapplication.view

import android.app.Application
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.LoginState
import com.example.myapplication.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {

    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    val vm: LoginViewModel? = if (isPreview) {
        null
    } else {
        viewModel(
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
                context.applicationContext as Application
            )
        )
    }

    val state = if (isPreview) {
        remember { mutableStateOf(LoginState()) }.value
    } else {
        vm!!.state.collectAsState().value
    }

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    if (!isPreview) {
        LaunchedEffect(Unit) {
            vm?.crearUsuarioDemo()
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
                    .padding(innerPadding),
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
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Fast Cook!",
                            fontSize = 40.sp,
                            fontFamily = FontFamily.Cursive,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(bottom = 40.dp)
                        )

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
                            text = "Correo",
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = {
                                Text(
                                    "Ingrese su correo",
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = textFieldColors
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Contraseña",
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = pass,
                            onValueChange = { pass = it },
                            placeholder = {
                                Text(
                                    "Ingrese su contraseña",
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = textFieldColors
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                vm?.login(email, pass)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Text(
                                text = "Iniciar Sesión",
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = onNavigateToRegister,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(
                                2.dp,
                                MaterialTheme.colorScheme.onPrimary
                            )
                        ) {

                            Text(
                                text = "Registrar cuenta",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        LaunchedEffect(state.success) {

                            if (state.success && !isPreview) {

                                snackbarHostState.showSnackbar(
                                    message = state.message ?: "Inicio de sesión exitoso"
                                )

                                delay(900)

                                onLoginSuccess()
                            }
                        }

                        LaunchedEffect(state.message) {

                            if (!state.success) {

                                state.message?.let { msg ->

                                    snackbarHostState.showSnackbar(
                                        message = state.message ?: "No pudimos iniciar sesión, revise su correo y contraseña"
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        TextButton(
                            onClick = onNavigateToRegister
                        ) {

                            Text(
                                text = "¿No tienes cuenta? Regístrate",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    LoginScreen(
        onLoginSuccess = {},
        onNavigateToRegister = {}
    )
}