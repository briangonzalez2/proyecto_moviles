package com.example.myapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun MainMenuScreen(
    onNavigate: (String) -> Unit = {}
) {

    val background = Color(0xFFF7C879)
    val brown = Color(0xFF5D3A00)

    val featuredRecipes = listOf("Receta", "Receta", "Receta")
    val favoriteRecipes = listOf("Receta", "Receta", "Receta", "Receta")
    val historyRecipes = listOf("Receta", "Receta", "Receta", "Receta", "Receta")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        SearchBar()

        Spacer(Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { SectionTitle("Recetas destacadas") }

            items(featuredRecipes) {
                RecipeCard(
                    title = it,
                    subtitle = "Menu description.",
                    icon = R.drawable.ic_arrow_up
                )
            }

            item { SectionTitle("Recetas favoritas") }

            items(favoriteRecipes) {
                RecipeCard(
                    title = it,
                    subtitle = "Nombre Chef",
                    icon = R.drawable.ic_arrow_up
                )
            }

            item { SectionTitle("Historial de recetas") }

            items(historyRecipes) {
                RecipeCard(
                    title = it,
                    subtitle = "Nombre Chef",
                    icon = R.drawable.ic_arrow_up
                )
            }
        }
    }
}



@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Buscar…", fontSize = 18.sp) },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                tint = Color.Black
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(15.dp)
    )
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF5D3A00),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun RecipeCard(title: String, subtitle: String, icon: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(subtitle, fontSize = 14.sp, color = Color.Gray)
            }

            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainMenuScreen() {
    MainMenuScreen()
}

