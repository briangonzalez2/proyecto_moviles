package com.example.myapplication.view

import android.app.Application
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.RecetaEntity
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.RecetaDetalleViewModel

@Composable
fun RecetasScreen(
    recetaId: Int,
    vm: RecetaDetalleViewModel = viewModel()
) {

    val isPreview = LocalInspectionMode.current

    // Collect state
    val receta by vm.receta.collectAsState()

    if (!isPreview) {

        LaunchedEffect(recetaId) {
            vm.cargarReceta(recetaId)
        }
    }

    MyApplicationTheme(dynamicColor = false) {

        RecetasScreenContent(
            receta = receta
        )
    }
}

/**
 * Stateless version for Preview support
 */
@Composable
fun RecetasScreenContent(
    receta: RecetaEntity?
) {

    var favorito by remember {
        mutableStateOf(false)
    }

    var rating by remember {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {

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
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        IconButton(
                            onClick = {
                                favorito = !favorito
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint =
                                    if (favorito)
                                        androidx.compose.ui.graphics.Color.Red
                                    else
                                        MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }

                // AUTOR
                item {

                    Text(
                        text = "Creado por: ${recetaData.autor}",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
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
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "${recetaData.tiempo} min",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // DESCRIPCION
                item {

                    Text(
                        text = "Descripción",
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = recetaData.descripcion,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // RATING
                item {

                    Text(
                        text = "Valoración",
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {

                        repeat(5) { index ->

                            IconButton(
                                onClick = {
                                    rating = index + 1
                                }
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint =
                                        if (index < rating)
                                            androidx.compose.ui.graphics.Color.Yellow
                                        else
                                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f),
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecetasScreenPreview() {

    MyApplicationTheme(dynamicColor = false) {

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
}
