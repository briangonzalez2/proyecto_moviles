package com.example.myapplication.view

import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun CrearRecetaScreen(
    onBack: () -> Unit,
    onCrear: (String, String, String, String, Uri?) -> Unit
) {
    val context = LocalContext.current

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

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tiempo,
            onValueChange = { tiempo = it },
            label = { Text("Tiempo (min)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dificultad,
            onValueChange = { dificultad = it },
            label = { Text("Dificultad (fácil/media/difícil)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { launcher.launch("image/*") }) {
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

        Button(
            onClick = {
                onCrear(titulo, descripcion, tiempo, dificultad, imagenUri)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear receta")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}

