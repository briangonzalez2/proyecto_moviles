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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppScaffold(
    onNavigate: (String) -> Unit = {},
    content: @Composable () -> Unit
) {
    var sidebarOpen by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {

        // ----------- CONTENT -----------
        Column {
            TopBar(
                onMenuClick = { sidebarOpen = true },
                onProfileClick = { onNavigate("perfil") }
            )
            content()
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
                onClose = { sidebarOpen = false },
                onNavigate = { route ->
                    sidebarOpen = false
                    onNavigate(route)
                }
            )
        }

        // ----------- DARK OVERLAY WHEN SIDEBAR IS OPEN -----------
        // OVERLAY (se dibuja antes del sidebar)
        if (sidebarOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { sidebarOpen = false }
            )
        }

        // SIDEBAR (encima de todo)
        AnimatedVisibility(
            visible = sidebarOpen,
            enter = slideInHorizontally(initialOffsetX = { -300 }),
            exit = slideOutHorizontally(targetOffsetX = { -300 })
        ) {
            Sidebar(
                onClose = { sidebarOpen = false },
                onNavigate = { route ->
                    sidebarOpen = false
                    onNavigate(route)
                }
            )
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
            .background(Color(0xFFF7C879))
            .padding(16.dp)
    ) {
        Column {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "close",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onClose() }
            )

            Spacer(Modifier.height(20.dp))

            SidebarItem(Icons.Default.Home, "Inicio") { onNavigate("home") }
            SidebarItem(Icons.Default.FilterList, "Mis recetas") { onNavigate("recetas") }
            //Aqui tambien va el de perfil
            SidebarItem(Icons.Default.Person, "Perfil") { onNavigate("perfil") }
            SidebarItem(Icons.Default.Settings, "Configuración") { onNavigate("config") }
        }
    }
}

@Composable
fun SidebarItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(26.dp))
        Spacer(Modifier.width(12.dp))
        Text(text, fontSize = 18.sp, color = Color(0xFF3A2500))
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
            modifier = Modifier
                .size(32.dp)
                .clickable { onMenuClick() }
        )

        Text(
            text = "Fast Cook!",
            fontSize = 32.sp,
            fontFamily = FontFamily.Cursive,
            color = Color(0xFF603800)
        )

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clickable { onProfileClick() }
        )
    }
}
