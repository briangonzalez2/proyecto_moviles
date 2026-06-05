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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun ConfigScreen(
    onBack: () -> Unit = {}
) {

    var username by remember { mutableStateOf("") }
    var darkMode by remember { mutableStateOf(false) }
    var notifAll by remember { mutableStateOf(false) }
    var notifMentions by remember { mutableStateOf(false) }

    MyApplicationTheme(dynamicColor = false) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                item {

                    Text(
                        text = "Administración de cuenta",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }

                // --- Nombre ---
                item {

                    Text(
                        text = "Nombre",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(12.dp),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            cursorColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),

                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Text("Cambiar nombre")
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }


                // --- Idioma ---
                item {

                    Text(
                        text = "Idioma",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    SettingsIconContainer(
                        shape = RoundedCornerShape(8.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // --- Notificaciones ---
                item {

                    Text(
                        text = "Notificaciones",

                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),

                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.SpaceBetween,

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Recibir todas",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Switch(
                            checked = notifAll,
                            onCheckedChange = { notifAll = it },

                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.SpaceBetween,

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Recibir solo menciones",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Switch(
                            checked = notifMentions,
                            onCheckedChange = { notifMentions = it },

                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))
                }

                // --- Guardar ---
                item {

                    Button(
                        onClick = { },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
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
                        onClick = { },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),

                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Text("Cerrar Sesión")
                    }

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun SettingsIconContainer(
    shape: Shape,
    content: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = shape
            )
            .padding(8.dp),

        contentAlignment = Alignment.Center
    ) {

        content()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConfigScreen() {

    ConfigScreen()
}