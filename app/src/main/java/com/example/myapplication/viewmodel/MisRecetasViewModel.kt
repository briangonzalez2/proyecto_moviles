package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.RecetaEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MisRecetasViewModel(application: Application)
    : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)

    private val recetaDao = db.recetaDao()

    private val _recetas =
        MutableStateFlow<List<RecetaEntity>>(emptyList())

    val recetas: StateFlow<List<RecetaEntity>>
            = _recetas

    fun cargarRecetas(usuario: String) {

        println("BUSCANDO RECETAS DE = $usuario")

        viewModelScope.launch {

            val lista = recetaDao.obtenerRecetasDeUsuario(usuario)

            println("RECETAS ENCONTRADAS = ${lista.size}")

            _recetas.value = lista
        }
    }
}