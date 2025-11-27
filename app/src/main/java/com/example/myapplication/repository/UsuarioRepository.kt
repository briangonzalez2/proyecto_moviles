package com.example.myapplication.repository

import com.example.myapplication.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UsuarioRepository(private val api: ApiService) {

    suspend fun register(nombre: String, contrasena: String) =
        api.register(nombre, contrasena)

    suspend fun login(correo: String, contrasena: String) =
        api.login(correo, contrasena)

    // ✔ Usa la ruta corregida del ApiService
    suspend fun uploadProfileImage(image: MultipartBody.Part, id: RequestBody) =
        api.uploadProfileImage(image, id)
}
