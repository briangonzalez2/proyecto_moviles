package com.example.myapplication.view

import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.session.UserSession
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.CrearRecetaViewModel



@Composable
fun CrearRecetaScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    // Detect if we are in Preview to avoid ViewModel initialization issues
    val vm: CrearRecetaViewModel? = if (isPreview) {
        null
    } else {
        viewModel(
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
                context.applicationContext as Application
            )
        )
    }

    // Handle UserSession safely for Previews
    val nombreUsuario by if (isPreview) {
        remember { mutableStateOf("Usuario") }
    } else {
        val session = remember { UserSession(context) }
        session.nombreUsuario.collectAsState(initial = "Usuario")
    }

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var dificultad by remember { mutableStateOf("media") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imagenUri = uri
    }

    val state = if (isPreview) {
        remember { mutableStateOf(CrearRecetaViewModel.CrearRecetaState()) }.value
    } else {
        vm!!.state.collectAsState().value
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(state.message) {

        state.message?.let { msg ->

            snackbarHostState.showSnackbar(
                message = msg
            )
        }
    }

    // Use the app theme and a Surface with the Primary color for the background.
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        cursorColor = MaterialTheme.colorScheme.onPrimary
                    )

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = tiempo,
                        onValueChange = { tiempo = it },
                        label = { Text("Tiempo (min)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = dificultad,
                        onValueChange = { dificultad = it },
                        label = { Text("Dificultad") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            launcher.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Seleccionar Imagen")
                    }

                    imagenUri?.let {
                        Spacer(modifier = Modifier.height(12.dp))

                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Reversed button colors: 'Crear receta' now uses Secondary
                    Button(
                        onClick = {

                            vm?.crearReceta(
                                titulo,
                                descripcion,
                                tiempo,
                                dificultad,
                                imagenUri,
                                nombreUsuario
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Crear receta")
                    }

                    if (state.success && !isPreview) {

                        LaunchedEffect(state.success) {

                            kotlinx.coroutines.delay(1500)

                            onBack()
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Reversed button colors: 'Cancelar' now uses OnPrimary
                    Button(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CrearRecetaScreenPreview() {
    CrearRecetaScreen(
        onBack = {}
    )
}