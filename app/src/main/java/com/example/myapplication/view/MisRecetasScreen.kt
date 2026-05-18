package com.example.myapplication.view

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.session.UserSession
import com.example.myapplication.ui.theme.Orange80
import com.example.myapplication.viewmodel.MisRecetasViewModel

@Composable
fun MisRecetasScreen(
    onRecipeClick: (String) -> Unit = {},
    onCreateNewRecipe: () -> Unit = {}
) {

    val background = Color(Orange80.toArgb())

    val context = LocalContext.current

    val session = remember {
        UserSession(context)
    }

    val nombreUsuario by session.nombreUsuario.collectAsState(initial = "")

    val vm: MisRecetasViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            context.applicationContext as Application
        )
    )

    val recetas by vm.recetas.collectAsState()

    LaunchedEffect(nombreUsuario) {

        if (nombreUsuario.isNotEmpty()) {
            vm.cargarRecetas(nombreUsuario)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = "Mis Recetas",
                fontSize = 32.sp,
                color = Color(0xFF5A3A00),
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
                            color = Color.DarkGray
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
                    containerColor = Color(0xFF6A4500),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    "Crear nueva receta",
                    fontSize = 18.sp
                )
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
            containerColor = Color.White
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
                    color = Color(0xFF5A3A00),
                    fontWeight = FontWeight.SemiBold
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Ver receta",
                    tint = Color(0xFF5A3A00)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color(0xFF5A3A00)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MisRecetasScreenPreview() {
    MisRecetasScreen()
}
