package com.example.myapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun ProfileScreen(
    userName: String = "Nombre",
    recipes: List<String> = listOf("Receta", "Receta", "Receta", "Receta"),
    comments: List<Pair<String, String>> = listOf(
        "Cristian Martínez" to "Excelente platillo, muy delicioso y bien explicado"
    ),
    onBack: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    onRecipeClick: (String) -> Unit = {}
) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7C474))
            .verticalScroll(scroll)
            .padding(16.dp)
    ) {

        // HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onBack() }
            )

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(Modifier.height(10.dp))

        // LOGO
        Text(
            "Fast Cook!",
            fontSize = 38.sp,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(20.dp))

        // BIG AVATAR
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User avatar",
            tint = Color(0xFF6B58A5),
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(12.dp))

        // USER NAME
        Text(
            userName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF68400A)
        )

        Spacer(Modifier.height(16.dp))

        // -----------------------
        // RECIPES SECTION
        // -----------------------
        Text(
            "Recetas:",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF68400A)
        )

        Spacer(Modifier.height(6.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(Modifier.padding(10.dp)) {
                recipes.forEachIndexed { index, recipe ->

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                onRecipeClick(recipe)
                                onNavigate("recetas")
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.StarBorder,
                                contentDescription = null
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(recipe, fontSize = 18.sp)
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null
                        )
                    }

                    if (index < recipes.lastIndex) {
                        Divider()
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // -----------------------
        // COMMENTS SECTION
        // -----------------------
        Text(
            "Comentarios:",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF68400A)
        )

        Spacer(Modifier.height(10.dp))

        comments.forEach { (author, text) ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text(author, fontWeight = FontWeight.Bold)
                        Text(text, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun profilePreview(){
    ProfileScreen(

    )
}

