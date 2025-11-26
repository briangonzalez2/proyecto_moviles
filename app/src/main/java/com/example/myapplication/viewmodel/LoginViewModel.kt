package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val message: String? = null
)

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun login(correo: String, contrasena: String, session: UserSession) {
        viewModelScope.launch {
            try {
                _state.value = LoginState(loading = true)

                val api = RetrofitClient.instance.create(com.example.myapplication.network.ApiService::class.java)
                val response: LoginResponse = api.login(correo, contrasena)

                if (response.success) {

                    // Guardar datos en sesión
                    session.saveUser(
                        id = response.id_usuario ?: 0,
                        nombre = response.nombre_usuario ?: "",
                        correo = correo
                    )

                    // Éxito
                    _state.value = LoginState(
                        loading = false,
                        success = true,
                        message = "Inicio de sesión exitoso"
                    )
                } else {
                    _state.value = LoginState(
                        loading = false,
                        success = false,
                        message = response.message ?: "Credenciales incorrectas"
                    )
                }

            } catch (e: Exception) {
                _state.value = LoginState(
                    loading = false,
                    success = false,
                    message = "Error de conexión: ${e.message}"
                )
            }
        }
    }
}
