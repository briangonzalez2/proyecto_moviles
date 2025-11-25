package com.example.myapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Orange80


@Composable
fun MisRecetasScreen(
    onRecipeClick: (String) -> Unit = {},
    onCreateNewRecipe: () -> Unit = {}
) {

    val background = Color(Orange80.toArgb())

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
                "Mis Recetas",
                fontSize = 32.sp,
                color = Color(0xFF5A3A00),
                fontWeight = FontWeight.Bold
            )

            // ------- LISTA DE RECETAS --------
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxHeight(0.85f)
            ) {
                items(5) { index ->
                    RecetaCardItem(
                        title = "Mi receta #$index",
                        description = "Descripción breve de la receta...",
                        onClick = { onRecipeClick("id_$index") }
                    )
                }
            }

            // ------- BOTÓN CREAR RECETA -------
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
                Text("Crear nueva receta", fontSize = 18.sp)
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
            .clickable { onClick() },
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
                    title,
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
                description,
                fontSize = 14.sp,
                color = Color(0xFF5A3A00)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun recetasPreview(){
    MisRecetasScreen(

    )
}
