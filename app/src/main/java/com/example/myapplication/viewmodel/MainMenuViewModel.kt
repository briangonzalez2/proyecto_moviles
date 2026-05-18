package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.RecetaEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainMenuViewModel(application: Application)
    : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)

    private val recetaDao = db.recetaDao()

    private val favoritoDao = db.favoritoDao()

    // DESTACADAS
    private val _recetasDestacadas =
        MutableStateFlow<List<RecetaEntity>>(emptyList())

    val recetasDestacadas:
            StateFlow<List<RecetaEntity>>
            = _recetasDestacadas

    // FAVORITAS
    private val _recetasFavoritas =
        MutableStateFlow<List<RecetaEntity>>(emptyList())

    val recetasFavoritas:
            StateFlow<List<RecetaEntity>>
            = _recetasFavoritas

    init {

        cargarDestacadas()
    }

    private fun cargarDestacadas() {

        viewModelScope.launch {

            _recetasDestacadas.value =
                recetaDao.obtenerRecientes()
        }
    }

    fun cargarFavoritas(usuario: String) {

        viewModelScope.launch {

            _recetasFavoritas.value =
                favoritoDao.obtenerFavoritas(usuario)
        }
    }
}