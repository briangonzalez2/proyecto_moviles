package com.example.myapplication.model

data class RecetaDetalle(
    val receta: Receta,
    val ingredientes: List<Ingrediente>,
    val pasos: List<Paso>
)