package com.example.myapplication.model

data class ReviewRequest(
    val id_usuario:Int,
    val id_receta:Int,
    val calificacion:Int,
    val comentario:String?
)