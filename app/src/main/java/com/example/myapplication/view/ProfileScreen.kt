package com.example.myapplication.view

import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.RecetaEntity
import com.example.myapplication.data.UsuarioEntity
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    onRecipeClick: (Int) -> Unit = {}
) {

    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    val vm: ProfileViewModel? = if (isPreview) {
        null
    } else {
        viewModel(
            factory = (context.applicationContext as? Application)?.let {
                ViewModelProvider.AndroidViewModelFactory.getInstance(it)
            }
        )
    }

    val user = if (isPreview) {
        UsuarioEntity(
            nombre_usuario = "Usuario Preview",
            email = "preview@test.com",
            password = "",
            role = "chef"
        )
    } else {
        vm?.usuario?.collectAsState()?.value
    }

    val recetas = if (isPreview) {
        listOf(
            RecetaEntity(
                titulo = "Pasta Carbonara",
                descripcion = "Receta italiana",
                tiempo = "20 min",
                dificultad = "Media",
                imagenUri = null,
                autor = "Preview"
            ),
            RecetaEntity(
                titulo = "Hamburguesa",
                descripcion = "Casera",
                tiempo = "15 min",
                dificultad = "Fácil",
                imagenUri = null,
                autor = "Preview"
            )
        )
    } else {
        vm?.recetas?.collectAsState()?.value ?: emptyList()
    }

    val imageUrl = if (isPreview) {
        null
    } else {
        vm?.profileImageUrl?.collectAsState()?.value
    }

    MyApplicationTheme(dynamicColor = false) {

        ProfileContent(
            user = user,
            recetas = recetas,
            imageUrl = imageUrl,
            onBack = onBack,
            onRecipeClick = onRecipeClick,
            onImageSelected = {
                vm?.guardarFotoPerfil(it)
            }
        )
    }
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

                context.contentResolver.takePersistableUriPermission(
                    it,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

            } catch (_: Exception) {
            }

            onImageSelected(it)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            onBack()
                        }
                )

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            // LOGO
            Text(
                text = "Fast Cook!",
                fontSize = 38.sp,
                fontFamily = FontFamily.Cursive,
                color = MaterialTheme.colorScheme.onPrimary,
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
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.primary
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
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // RECETAS
            Text(
                text = "Mis recetas",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
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
                                    tint = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = receta.titulo,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        if (index < recetas.lastIndex) {

                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // COMENTARIOS
            Text(
                text = "Comentarios",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            repeat(2) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),

                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(42.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {

                            Text(
                                text = "Usuario",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = "Muy buena receta 👌",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {

    ProfileScreen()
}