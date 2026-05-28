package com.example.myapplication.view

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.session.UserSession
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.MainMenuViewModel
import kotlinx.coroutines.launch

@Composable
fun MainMenuScreen(
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit = {}
) {

    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    // SESSION
    val session = remember { UserSession(context) }

    val scope = rememberCoroutineScope()

    val nombre by session.nombreUsuario.collectAsState(initial = "")

    // VIEWMODEL
    val vm: MainMenuViewModel? = if (isPreview) {
        null
    } else {
        viewModel(
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
                context.applicationContext as Application
            )
        )
    }

    // RECETAS
    val recetas = if (isPreview) {
        emptyList()
    } else {
        vm?.recetasDestacadas?.collectAsState()?.value ?: emptyList()
    }

    val favoritas = if (isPreview) {
        emptyList()
    } else {
        vm?.recetasFavoritas?.collectAsState()?.value ?: emptyList()
    }

    // CARGAR FAVORITAS
    if (!isPreview) {

        LaunchedEffect(nombre) {

            if (nombre.isNotEmpty()) {

                vm?.cargarFavoritas(nombre)
            }
        }
    }

    MyApplicationTheme(dynamicColor = false) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {

                Spacer(Modifier.height(10.dp))

                // HEADER
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {

                        Text(
                            text = "¡Bienvenido!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = nombre.ifEmpty { "Usuario" },
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    IconButton(
                        onClick = {

                            scope.launch {

                                session.clearSession()

                                onLogout()
                            }
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Cerrar sesión",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                SearchBar()

                Spacer(Modifier.height(20.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    // DESTACADAS
                    item {

                        SectionTitle("Recetas destacadas")
                    }

                    items(recetas) { receta ->

                        RecipeCard(
                            title = receta.titulo,
                            subtitle = receta.descripcion,
                            icon = R.drawable.ic_arrow_up,
                            onClick = {

                                onNavigate("receta/${receta.id}")
                            }
                        )
                    }

                    // FAVORITAS
                    if (favoritas.isNotEmpty()) {

                        item {

                            Spacer(modifier = Modifier.height(10.dp))

                            SectionTitle("Recetas favoritas")
                        }

                        items(favoritas) { receta ->

                            RecipeCard(
                                title = receta.titulo,
                                subtitle = receta.descripcion,
                                icon = R.drawable.ic_arrow_up,
                                onClick = {

                                    onNavigate("receta/${receta.id}")
                                }
                            )
                        }
                    }

                    // HISTORIAL
                    item {

                        Spacer(modifier = Modifier.height(10.dp))

                        SectionTitle("Historial de recetas")
                    }

                    items(recetas.take(5)) { receta ->

                        RecipeCard(
                            title = receta.titulo,
                            subtitle = receta.descripcion,
                            icon = R.drawable.ic_arrow_up,
                            onClick = {

                                onNavigate("receta/${receta.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar() {

    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {

            text = it
        },
        placeholder = {

            Text(
                "Buscar…",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        },
        leadingIcon = {

            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(15.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun SectionTitle(text: String) {

    Text(
        text = text,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun RecipeCard(
    title: String,
    subtitle: String,
    icon: Int,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable {

                onClick()
            },

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
            }

            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
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