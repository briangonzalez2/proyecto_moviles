package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface FavoritoDao {

    @Insert
    suspend fun agregarFavorito(favorito: FavoritoEntity)

    @Query("""
        DELETE FROM favoritos
        WHERE usuario = :usuario
        AND recetaId = :recetaId
    """)
    suspend fun eliminarFavorito(
        usuario: String,
        recetaId: Int
    )

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM favoritos
            WHERE usuario = :usuario
            AND recetaId = :recetaId
        )
    """)
    suspend fun esFavorita(
        usuario: String,
        recetaId: Int
    ): Boolean

    @Query("""
        SELECT recetas.*
        FROM recetas
        INNER JOIN favoritos
        ON recetas.id = favoritos.recetaId
        WHERE favoritos.usuario = :usuario
    """)
    suspend fun obtenerFavoritas(
        usuario: String
    ): List<RecetaEntity>
}