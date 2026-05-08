package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.UsuarioEntity
import com.example.myapplication.model.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val usuarioDao = db.usuarioDao()

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun register(
        nombre: String,
        email: String,
        password: String,
        role: String
    ) {

        viewModelScope.launch {

            if (
                nombre.isBlank() ||
                email.isBlank() ||
                password.isBlank()
            ) {

                _state.value = RegisterState(
                    success = false,
                    message = "Completa todos los campos"
                )

                return@launch
            }

            // Verificar si ya existe
            val existingUser = usuarioDao.buscarPorEmail(email)

            if (existingUser != null) {

                _state.value = RegisterState(
                    success = false,
                    message = "El correo ya está registrado"
                )

                return@launch
            }

            // Crear usuario
            usuarioDao.insertarUsuario(
                UsuarioEntity(
                    nombre_usuario = nombre,
                    email = email,
                    password = password,
                    role = role
                )
            )

            _state.value = RegisterState(
                success = true,
                message = "Usuario registrado correctamente"
            )
        }
    }
}