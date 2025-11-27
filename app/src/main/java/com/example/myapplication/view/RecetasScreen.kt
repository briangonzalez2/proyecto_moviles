package com.example.myapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Composable
fun RecetasScreen(
    onRecipeClick: () -> Unit = {}   // ← agregado para navegación
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFB948)) // Color amarillo similar
    ) {}
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )

    {

        item {
            Text(
                text = "Fast Cook!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Imagen Placeholder")
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Tag("#Italiana")
                    Tag("#pasta")
                }
                Text(
                    "Receta de: Juan Florez",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Spaghetti Italiano",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("30 min")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Ingredientes:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column {
                IngredientItem("Pasta")
                IngredientItem("-")
                IngredientItem("-")
                IngredientItem("2 litros de agua")
                IngredientItem("5g de sal")
                IngredientItem("-")
                IngredientItem("-")
                IngredientItem("1 tarro de salsa boloñesa")
                IngredientItem("-")
                IngredientItem("300g de carne molida")
                IngredientItem("-")
                IngredientItem("200g Queso Parmesano")
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Text(
                text = "Procedimiento",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text =
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Text(
                text = "Valoración:  4.0",
                style = MaterialTheme.typography.titleMedium
            )

            Row {
                repeat(4) {
                    Icon(Icons.Filled.Star, contentDescription = null)
                }
                Icon(Icons.Filled.StarBorder, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Text(
                text = "Comentarios (1):",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {

                    Text(
                        text = "Cristian Martinez",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {
                        repeat(4) {
                            Icon(Icons.Filled.Star, contentDescription = null, modifier = Modifier.height(18.dp))
                        }
                        Icon(Icons.Filled.StarBorder, contentDescription = null, modifier = Modifier.height(18.dp))
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Excelente platillo, muy delicioso y bien explicado")
                }
            }
        }
    }
}


// -----------------------------
// ELEMENTOS REUTILIZABLES
// -----------------------------

@Composable
fun Tag(text: String) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .background(Color(0xFFE0E0E0), RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun IngredientItem(name: String) {
    Text("• $name", style = MaterialTheme.typography.bodyLarge)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun recetas2Preview(){
    RecetasScreen(

    )
}