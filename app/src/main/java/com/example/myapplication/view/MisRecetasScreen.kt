package com.example.myapplication.view

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.session.UserSession
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.MisRecetasViewModel

@Composable
fun MisRecetasScreen(
    onRecipeClick: (String) -> Unit = {},
    onCreateNewRecipe: () -> Unit = {}
) {

    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    val session = remember {
        UserSession(context)
    }

    val nombreUsuario by session.nombreUsuario.collectAsState(initial = "")

    val vm: MisRecetasViewModel? = if (isPreview) {
        null
    } else {
        viewModel(
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
                context.applicationContext as Application
            )
        )
    }

    val recetas = if (isPreview) {
        emptyList()
    } else {
        vm?.recetas?.collectAsState()?.value ?: emptyList()
    }

    if (!isPreview) {

        LaunchedEffect(nombreUsuario) {

            if (nombreUsuario.isNotEmpty()) {
                vm?.cargarRecetas(nombreUsuario)
            }
        }
    }

    MyApplicationTheme(dynamicColor = false) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Text(
                        text = "Mis Recetas",
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {

                        if (recetas.isEmpty()) {

                            item {

                                Text(
                                    text = "Aún no has creado recetas",
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            }

                        } else {

                            items(recetas) { receta ->

                                RecetaCardItem(
                                    title = receta.titulo,
                                    description = receta.descripcion,
                                    onClick = {
                                        onRecipeClick(receta.id.toString())
                                    }
                                )
                            }
                        }
                    }

                    Button(
                        onClick = onCreateNewRecipe,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Text(
                            text = "Crear nueva receta",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecetaCardItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = title,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Ver receta",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MisRecetasScreenPreview() {

    MisRecetasScreen()
}