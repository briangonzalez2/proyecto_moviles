package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecetaDao {

    @Insert
    suspend fun insertarReceta(receta: RecetaEntity)

    @Query("SELECT * FROM recetas ORDER BY RANDOM() LIMIT 5")
    suspend fun obtenerRandom(): List<RecetaEntity>

    @Query("SELECT * FROM recetas ORDER BY timestamp DESC LIMIT 5")
    suspend fun obtenerRecientes(): List<RecetaEntity>

    @Query("SELECT * FROM recetas WHERE autor = :autor ORDER BY timestamp DESC")
    suspend fun obtenerRecetasDeUsuario(
        autor: String
    ): List<RecetaEntity>

    @Query("SELECT * FROM recetas WHERE id = :id")
    suspend fun obtenerPorId(id: Int): RecetaEntity?

    @Query("SELECT * FROM recetas WHERE autor = :autor")
    suspend fun obtenerPorAutor(autor: String): List<RecetaEntity>
}