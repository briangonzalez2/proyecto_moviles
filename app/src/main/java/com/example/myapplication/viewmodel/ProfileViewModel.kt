package com.example.myapplication.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.RecetaEntity
import com.example.myapplication.data.UsuarioEntity
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)

    private val usuarioDao = db.usuarioDao()

    private val recetaDao = db.recetaDao()

    private val session =
        UserSession(application)

    // USUARIO
    private val _usuario =
        MutableStateFlow<UsuarioEntity?>(null)

    val usuario: StateFlow<UsuarioEntity?> =
        _usuario

    // RECETAS
    private val _recetas =
        MutableStateFlow<List<RecetaEntity>>(emptyList())

    val recetas:
            StateFlow<List<RecetaEntity>>
            = _recetas

    // FOTO PERFIL
    private val _profileImageUrl =
        MutableStateFlow<String?>(null)

    val profileImageUrl:
            StateFlow<String?>
            = _profileImageUrl

    init {

        cargarUsuario()
    }

    private fun cargarUsuario() {

        viewModelScope.launch {

            session.nombreUsuario.collect { nombre ->

                if (nombre.isNotEmpty()) {

                    val user =
                        usuarioDao.obtenerUsuario(nombre)

                    _usuario.value = user

                    _profileImageUrl.value =
                        user?.fotoPerfil

                    _recetas.value =
                        recetaDao.obtenerPorAutor(nombre)
                }
            }
        }
    }

    fun guardarFotoPerfil(uri: Uri) {

        viewModelScope.launch {

            val user = _usuario.value
                ?: return@launch

            val nuevaFoto =
                uri.toString()

            // guardar en Room
            usuarioDao.actualizarFoto(
                user.id_usuario,
                nuevaFoto
            )

            // actualizar usuario en memoria
            val updatedUser =
                user.copy(
                    fotoPerfil = nuevaFoto
                )

            _usuario.value =
                updatedUser

            _profileImageUrl.value =
                nuevaFoto
        }
    }
}