package com.example.myapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.ApiService
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RecetasViewModel(
    private val context: Context,
    private val session: UserSession
) : ViewModel() {

    private val api = RetrofitClient.instance.create(ApiService::class.java)

    private val _misRecetas = MutableStateFlow<List<Map<String, Any?>>>(emptyList())
    val misRecetas: StateFlow<List<Map<String, Any?>>> = _misRecetas


    // ----------------------------------------------------------
    // LISTAR SOLO LAS RECETAS DEL CHEF LOGUEADO
    // ----------------------------------------------------------
    fun listarMisRecetas() {
        viewModelScope.launch {
            try {
                val idUsuario = session.idUsuario.first()

                val chefResponse = api.getChefId(idUsuario)
                val idChef = chefResponse.id_chef ?: run {
                    Log.e("RecetasViewModel", "Usuario NO registrado en tabla Chef")
                    return@launch
                }

                val response = api.listarRecetas()

                if (response.success) {
                    val todas = response.data ?: emptyList()

                    val mias = todas.filter { receta ->
                        receta["id_chef"]?.toString() == idChef.toString()
                    }

                    _misRecetas.value = mias

                } else {
                    Log.e("RecetasViewModel", "ListarRecetas -> success = false")
                }

            } catch (e: Exception) {
                Log.e("RecetasViewModel", "Error al listar recetas", e)
            }
        }
    }


    fun crearReceta(
        titulo: String,
        descripcion: String,
        tiempo: String,
        dificultad: String,
        imagenUri: Uri?
    ) {
        viewModelScope.launch {
            try {
                val idUsuario = session.idUsuario.first()

                val chefResponse = api.getChefId(idUsuario)
                val idChef = chefResponse.id_chef ?: run {
                    Log.e("RecetasViewModel", "El usuario no tiene registro en tabla Chef")
                    return@launch
                }

                // ---------- IMAGEN (con logs y validaciones) ----------
                val partImagen = imagenUri?.let { uri ->
                    try {
                        Log.d("RecetasVM", "URI recibida: $uri")

                        val input = context.contentResolver.openInputStream(uri)
                            ?: return@let run {
                                Log.e("RecetasVM", "ERROR: inputStream es NULL")
                                null
                            }

                        val tempFile = File.createTempFile("receta_", ".jpg", context.cacheDir)
                        Log.d("RecetasVM", "Temp file creado en: ${tempFile.absolutePath}")

                        tempFile.outputStream().use { output ->
                            input.copyTo(output)
                        }

                        val requestBody = tempFile.asRequestBody("image/*".toMediaType())
                        MultipartBody.Part.createFormData("imagen", tempFile.name, requestBody)
                    } catch (e: Exception) {
                        Log.e("RecetasVM", "ERROR al convertir imagen", e)
                        null
                    }
                }

                Log.d("RecetasVM", "Enviando receta -> titulo=$titulo, descripcion=$descripcion, tiempo=$tiempo, dificultad=$dificultad, imagen=${partImagen != null}")

                // ---------- LLAMADA A LA API ----------
                val response = api.crearReceta(
                    idChef.toString().toRequestBody("text/plain".toMediaType()),
                    titulo.toRequestBody("text/plain".toMediaType()),
                    descripcion.toRequestBody("text/plain".toMediaType()),
                    tiempo.toRequestBody("text/plain".toMediaType()),
                    dificultad.toRequestBody("text/plain".toMediaType()),
                    partImagen
                )

                Log.d("RecetasVM", "Respuesta crearReceta -> ${response.raw()}")

                if (response.isSuccessful && response.body()?.success == true) {
                    Log.d("RecetasViewModel", "Receta creada correctamente")
                    listarMisRecetas()
                } else {
                    Log.e("RecetasViewModel", "Error API: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.e("RecetasViewModel", "Error crearReceta()", e)
            }
        }
    }
}
