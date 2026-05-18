package com.example.myapplication.data

import androidx.room.Entity

@Entity(
    tableName = "favoritos",
    primaryKeys = ["usuario", "recetaId"]
)
data class FavoritoEntity(

    val usuario: String,

    val recetaId: Int
)