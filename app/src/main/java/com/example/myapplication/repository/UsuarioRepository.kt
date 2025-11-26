package com.example.myapplication.repository

import com.example.myapplication.model.Usuario
import com.example.myapplication.network.ApiService

class UsuarioRepository(private val api: ApiService) {

    suspend fun register(nombre: String, contrasena: String) =
        api.register(nombre, contrasena)

    suspend fun login(correo: String, contrasena: String) =
        api.login(correo, contrasena)
}

