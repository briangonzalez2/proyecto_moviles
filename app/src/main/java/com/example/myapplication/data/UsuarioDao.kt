package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertarUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :pass LIMIT 1")
    suspend fun login(email: String, pass: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE nombre_usuario = :nombre LIMIT 1")
    suspend fun obtenerUsuario(nombre: String): UsuarioEntity?

    @Query("UPDATE usuarios SET fotoPerfil = :foto WHERE id_usuario = :id")
    suspend fun actualizarFoto(id: Int, foto: String)
}