package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.RegisterState
import com.example.myapplication.network.ApiService
import com.example.myapplication.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val api = RetrofitClient.instance.create(ApiService::class.java)

    private val _state = MutableStateFlow<RegisterState?>(null)
    val state: StateFlow<RegisterState?> = _state

    fun register(nombre: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val response = api.register(nombre, contrasena)

                _state.value = RegisterState(
                    success = response.success,    // <- usar success
                    message = response.message
                )

            } catch (e: Exception) {
                _state.value = RegisterState(
                    success = false,
                    message = "Error al conectar con el servidor"
                )
            }
        }
    }
}

