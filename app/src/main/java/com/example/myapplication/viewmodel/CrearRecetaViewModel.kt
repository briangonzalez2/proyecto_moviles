package com.example.myapplication.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.RecetaEntity
import kotlinx.coroutines.launch

class CrearRecetaViewModel(application: Application)
    : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)

    private val recetaDao = db.recetaDao()

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
    }
}