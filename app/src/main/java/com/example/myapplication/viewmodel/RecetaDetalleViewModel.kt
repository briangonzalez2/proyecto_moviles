package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.RecetaEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecetaDetalleViewModel(application: Application)
    : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)

    private val recetaDao = db.recetaDao()

    private val _receta =
        MutableStateFlow<RecetaEntity?>(null)

    val receta: StateFlow<RecetaEntity?> = _receta

    fun cargarReceta(id: Int) {

        viewModelScope.launch {

            _receta.value =
                recetaDao.obtenerPorId(id)
        }
    }
}