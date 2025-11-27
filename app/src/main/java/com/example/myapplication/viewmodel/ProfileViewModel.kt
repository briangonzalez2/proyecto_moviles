
package com.example.myapplication.viewmodel

import android.content.Context
import android.net.Uri
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
//{"id":"51021","variant":"standard","title":"ProfileViewModel.kt FULL FIX"}
class ProfileViewModel(
    private val context: Context,
    private val session: UserSession
) : ViewModel() {

    // ← URL BASE del servidor
    private val baseUrl = "http://192.168.18.143/api/uploads/profile/"

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl

    private val api = RetrofitClient.instance.create(ApiService::class.java)

    fun uploadProfileImage(uri: Uri) {
        viewModelScope.launch {
            try {
                val idUsuario = session.idUsuario.first()
                if (idUsuario == 0) return@launch

                // Copiar imagen a archivo temporal
                val input = context.contentResolver.openInputStream(uri)
                val tempFile = File.createTempFile("profile", ".jpg", context.cacheDir)
                tempFile.outputStream().use { output -> input!!.copyTo(output) }

                // Preparar multipart
                val imageRequest = tempFile.asRequestBody("image/*".toMediaType())
                val imagePart = MultipartBody.Part.createFormData(
                    "image", tempFile.name, imageRequest
                )

                val idPart = idUsuario.toString()
                    .toRequestBody("text/plain".toMediaType())

                // Enviar a API
                val response = api.uploadProfileImage(imagePart, idPart)

                if (response.isSuccessful && response.body()?.success == true) {

                    val fileName = response.body()?.imageUrl

                    // Construir URL completa
                    if (!fileName.isNullOrEmpty()) {
                        _profileImageUrl.value = baseUrl + fileName
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
