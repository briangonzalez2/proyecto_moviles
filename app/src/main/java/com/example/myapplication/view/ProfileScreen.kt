package com.example.myapplication.view

import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ProfileViewModel
import coil.compose.AsyncImage
import com.example.myapplication.data.UsuarioEntity
import com.example.myapplication.data.RecetaEntity

@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    onRecipeClick: (Int) -> Unit = {}
) {

    val context = LocalContext.current

    // Using a safe cast and a nullable factory to avoid ClassCastException in Previews.
    // In Preview mode, applicationContext may not be an instance of Application.
    val vm: ProfileViewModel = viewModel(
        factory = (context.applicationContext as? Application)?.let {
            ViewModelProvider.AndroidViewModelFactory.getInstance(it)
        }
    )

    val user by vm.usuario.collectAsState()
    val recetas by vm.recetas.collectAsState()
    val imageUrl by vm.profileImageUrl.collectAsState()

    ProfileContent(
        user = user,
        recetas = recetas,
        imageUrl = imageUrl,
        onBack = onBack,
        onRecipeClick = onRecipeClick,
        onImageSelected = { vm.guardarFotoPerfil(it) }
    )
}

@Composable
fun ProfileContent(
    user: UsuarioEntity?,
    recetas: List<RecetaEntity>,
    imageUrl: String?,
    onBack: () -> Unit,
    onRecipeClick: (Int) -> Unit,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    val scroll = rememberScrollState()

    // SELECT IMAGE
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            try {
                // guardar permiso permanente
                context.contentResolver.takePersistableUriPermission(
                    it,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: Exception) {
                // Silently ignore permission errors in Preview
            }
            // guardar en Room/ViewModel
            onImageSelected(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7C474))
            .verticalScroll(scroll)
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

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
                    .clickable {
                        onBack()
                    }
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
            text = "Fast Cook!",
            fontSize = 38.sp,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // PROFILE IMAGE
        AsyncImage(
            model = imageUrl ?: R.drawable.ic_launcher_foreground,
            contentDescription = "Foto perfil",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(14.dp))

        // CHANGE IMAGE BUTTON
        Button(
            onClick = {
                launcher.launch(arrayOf("image/*"))
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6B4000)
            )
        ) {
            Text("Cambiar foto")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // USER NAME
        Text(
            text = user?.nombre_usuario ?: "Usuario",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF68400A)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // RECETAS
        Text(
            text = "Mis recetas",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF68400A)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                recetas.forEachIndexed { index, receta ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onRecipeClick(receta.id)
                            }
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = Color.Red
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = receta.titulo,
                                fontSize = 18.sp
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }

                    if (index < recetas.lastIndex) {
                        Divider()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // COMENTARIOS FAKE POR AHORA
        Text(
            text = "Comentarios",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF68400A)
        )

        Spacer(modifier = Modifier.height(12.dp))

        repeat(2) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(42.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Usuario",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Muy buena receta 👌",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileContent(
        user = UsuarioEntity(
            nombre_usuario = "Usuario de Prueba",
            email = "test@example.com",
            password = "",
            role = "chef"
        ),
        recetas = listOf(
            RecetaEntity(titulo = "Receta 1", descripcion = "", tiempo = "10 min", dificultad = "Fácil", imagenUri = null, autor = "Usuario de Prueba"),
            RecetaEntity(titulo = "Receta 2", descripcion = "", tiempo = "20 min", dificultad = "Media", imagenUri = null, autor = "Usuario de Prueba")
        ),
        imageUrl = null,
        onBack = {},
        onRecipeClick = {},
        onImageSelected = {}
    )
}