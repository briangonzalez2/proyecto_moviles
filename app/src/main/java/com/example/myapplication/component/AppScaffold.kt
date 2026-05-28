package com.example.myapplication.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun AppScaffold(
    onNavigate: (String) -> Unit = {},
    content: @Composable () -> Unit
) {

    var sidebarOpen by remember { mutableStateOf(false) }

    MyApplicationTheme(dynamicColor = false) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                // ----------- CONTENT -----------
                Column {

                    TopBar(
                        onMenuClick = { sidebarOpen = true },
                        onProfileClick = { onNavigate("perfil") }
                    )

                    content()
                }

                // ----------- OVERLAY -----------
                if (sidebarOpen) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable {
                                sidebarOpen = false
                            }
                    )
                }

                // ----------- SIDEBAR -----------
                AnimatedVisibility(
                    visible = sidebarOpen,

                    enter = slideInHorizontally(
                        initialOffsetX = { -300 },
                        animationSpec = tween(300)
                    ),

                    exit = slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(300)
                    )
                ) {

                    Sidebar(
                        onClose = {
                            sidebarOpen = false
                        },

                        onNavigate = { route ->

                            sidebarOpen = false
                            onNavigate(route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Sidebar(
    onClose: () -> Unit,
    onNavigate: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .width(230.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {

        Column {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "close",

                tint = MaterialTheme.colorScheme.onPrimary,

                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        onClose()
                    }
            )

            Spacer(Modifier.height(20.dp))

            SidebarItem(
                icon = Icons.Default.Home,
                text = "Inicio"
            ) {
                onNavigate("home")
            }

            SidebarItem(
                icon = Icons.Default.FilterList,
                text = "Mis recetas"
            ) {
                onNavigate("recetas")
            }

            SidebarItem(
                icon = Icons.Default.Person,
                text = "Perfil"
            ) {
                onNavigate("perfil")
            }

            SidebarItem(
                icon = Icons.Default.Settings,
                text = "Configuración"
            ) {
                onNavigate("config")
            }
        }
    }
}

@Composable
fun SidebarItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable {
                onClick()
            },

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,

            tint = MaterialTheme.colorScheme.onPrimary,

            modifier = Modifier.size(26.dp)
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun TopBar(
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 12.dp),

        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = null,

            tint = MaterialTheme.colorScheme.onPrimary,

            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onMenuClick()
                }
        )

        Text(
            text = "Fast Cook!",
            fontSize = 32.sp,
            fontFamily = FontFamily.Cursive,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,

            tint = MaterialTheme.colorScheme.onPrimary,

            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onProfileClick()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {

    AppScaffold {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Contenido principal",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}