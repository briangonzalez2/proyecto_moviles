package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.UsuarioEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginState(
    val success: Boolean = false,
    val message: String? = null
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val usuarioDao = db.usuarioDao()

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun login(email: String, pass: String) {

        viewModelScope.launch {

            if (email.isBlank() || pass.isBlank()) {

                _state.value = LoginState(
                    success = false,
                    message = "Completa todos los campos"
                )

                return@launch
            }

            val user = usuarioDao.login(email, pass)

            if (user != null) {

                _state.value = LoginState(
                    success = true,
                    message = "Bienvenido ${user.nombre_usuario}"
                )

            } else {

                _state.value = LoginState(
                    success = false,
                    message = "Credenciales incorrectas"
                )
            }
        }
    }

    fun crearUsuarioDemo() {

        viewModelScope.launch {

            usuarioDao.insertarUsuario(
                UsuarioEntity(
                    nombre_usuario = "admin",
                    email = "admin@test.com",
                    password = "1234",
                    role = "chef"
                )
            )
        }
    }
}