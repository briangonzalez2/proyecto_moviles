package com.example.myapplication.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConfigScreen(
    onBack: () -> Unit = {}   // ← agregado para evitar error en el NavHost
) {

    // Estados temporales (solo UI)
    var username by remember { mutableStateOf("Usuario") }
    var darkMode by remember { mutableStateOf(false) }
    var notifAll by remember { mutableStateOf(false) }
    var notifMentions by remember { mutableStateOf(false) }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Text(
                text = "Administracion de cuenta",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        // --- CAMPO DE NOMBRE ---
        item {
            Text("Nombre", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* cambiar nombre */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6E4800),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cambiar nombre")
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // --- Tema oscuro ---
        item {
            Text(
                "Tema Oscuro",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Brightness4,
                    contentDescription = null,
                    tint = Color(0xFF6E4800),
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFF9C979), CircleShape)
                        .padding(8.dp)
                )

                Switch(
                    checked = darkMode,
                    onCheckedChange = { darkMode = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF6E4800),
                        checkedTrackColor = Color(0xFFF5D18C)
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // --- Idioma ---
        item {
            Text(
                "Idioma",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = null,
                tint = Color(0xFF6E4800),
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF9C979), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        // --- Notificaciones ---
        item {
            Text(
                "Notificaciones",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Recibir todas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recibir todas", style = MaterialTheme.typography.titleMedium)

                Switch(
                    checked = notifAll,
                    onCheckedChange = { notifAll = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF6E4800),
                        checkedTrackColor = Color(0xFFF5D18C)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recibir menciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recibir solo menciones", style = MaterialTheme.typography.titleMedium)

                Switch(
                    checked = notifMentions,
                    onCheckedChange = { notifMentions = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF6E4800),
                        checkedTrackColor = Color(0xFFF5D18C)
                    )
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }

        // --- Botón guardar ---
        item {
            Button(
                onClick = { /* guardar cambios */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6E4800),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar Cambios")
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // --- Cerrar sesión ---
        item {
            Button(
                onClick = { /* cerrar sesión */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6E4800),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cerrar Sesión")
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConfigScreen() {
    ConfigScreen(onBack = {})
}
