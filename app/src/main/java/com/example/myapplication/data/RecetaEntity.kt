package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class RecetaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val titulo: String,

    val descripcion: String,

    val tiempo: String,

    val dificultad: String,

    val imagenUri: String?,

    val autor: String,

    val timestamp: Long = System.currentTimeMillis()
)