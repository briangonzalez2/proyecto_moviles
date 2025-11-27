package com.example.myapplication.model

data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val id_usuario: Int?,
    val nombre_usuario: String?
)
