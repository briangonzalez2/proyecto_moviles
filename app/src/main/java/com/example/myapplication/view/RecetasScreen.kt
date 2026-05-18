package com.example.myapplication.view

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.viewmodel.RecetaDetalleViewModel
import com.example.myapplication.data.RecetaEntity

@Composable
fun RecetasScreen(
    recetaId: Int,
    vm: RecetaDetalleViewModel = viewModel()
) {
    // Collect the state from the ViewModel
    val receta by vm.receta.collectAsState()

    LaunchedEffect(recetaId) {
        vm.cargarReceta(recetaId)
    }

    // Pass the state to the stateless Composable
    RecetasScreenContent(receta = receta)
}

/**
 * Stateless version of the RecetasScreen for better testability and Preview support.
 * This resolves the issue where ViewModels cannot be easily instantiated in Previews.
 */
@Composable
fun RecetasScreenContent(receta: RecetaEntity?) {
    receta?.let { recetaData ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // IMAGEN
            item {
                recetaData.imagenUri?.let { image ->
                    Image(
                        painter = rememberAsyncImagePainter(image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // TITULO + FAVORITO
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = recetaData.titulo,
                        fontSize = 30.sp,
                        color = Color(0xFF5A3A00)
                    )
                    IconButton(
                        onClick = {
                            // FAVORITOS LUEGO
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // AUTOR
            item {
                Text(
                    text = "Creado por: ${recetaData.autor}",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            // TIEMPO
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${recetaData.tiempo} min")
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // DESCRIPCION
            item {
                Text(
                    text = "Descripción",
                    fontSize = 22.sp,
                    color = Color(0xFF5A3A00)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = recetaData.descripcion
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // RATING
            item {
                Text(
                    text = "Valoración",
                    fontSize = 22.sp,
                    color = Color(0xFF5A3A00)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecetasScreenPreview() {
    // Using the stateless content Composable with mock data for the Preview
    RecetasScreenContent(
        receta = RecetaEntity(
            id = 1,
            titulo = "Receta de Ejemplo",
            descripcion = "Esta es una descripción detallada de la receta para mostrar en la vista previa.",
            tiempo = "45",
            dificultad = "Media",
            imagenUri = null,
            autor = "Chef de Pruebas"
        )
    )
}
