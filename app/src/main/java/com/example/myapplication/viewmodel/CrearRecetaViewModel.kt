package com.example.myapplication.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.RecetaEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CrearRecetaViewModel(application: Application)
    : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)

    private val recetaDao = db.recetaDao()

    private val _state = MutableStateFlow(CrearRecetaState())
    val state: StateFlow<CrearRecetaState> = _state

    fun crearReceta(
        titulo: String,
        descripcion: String,
        tiempo: String,
        dificultad: String,
        imagenUri: Uri?,
        autor: String
    ) {

        viewModelScope.launch {

            recetaDao.insertarReceta(

                RecetaEntity(
                    titulo = titulo,
                    descripcion = descripcion,
                    tiempo = tiempo,
                    dificultad = dificultad,
                    imagenUri = imagenUri?.toString(),
                    autor = autor
                )
            )
        }
        if (titulo.isBlank()) {
            _state.value = CrearRecetaState(
                success = false,
                message = "Ingresa un título para la receta"
            )
            return
        }

        if (descripcion.isBlank()) {
            _state.value = CrearRecetaState(
                success = false,
                message = "Ingresa una descripción"
            )
            return
        }

        if (tiempo.isBlank()) {
            _state.value = CrearRecetaState(
                success = false,
                message = "Ingresa el tiempo de preparación"
            )
            return
        }

        _state.value = CrearRecetaState(
            success = true,
            message = "Receta creada correctamente"
        )
    }
    data class CrearRecetaState(
        val success: Boolean = false,
        val message: String? = null
    )
}