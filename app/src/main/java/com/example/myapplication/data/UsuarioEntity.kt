package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id_usuario: Int = 0,
    val nombre_usuario: String,
    val email: String,
    val password: String,
    val role: String, // chef o user
    val fotoPerfil: String? = null
)