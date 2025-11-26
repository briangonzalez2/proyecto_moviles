package com.example.myapplication.network

import com.example.myapplication.model.FavoritoRequest
import com.example.myapplication.model.GenericResponse
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.RecetaDetalleResponse
import com.example.myapplication.model.RecetasResponse
import com.example.myapplication.model.ReviewRequest
import com.example.myapplication.model.ToggleResponse
import com.example.myapplication.model.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("api/usuario/register.php")
    suspend fun register(
        @Field("nombre") nombre: String,
        @Field("contrasena") contrasena: String
    ): GenericResponse

    @FormUrlEncoded
    @POST("api/usuario/login.php")
    suspend fun login(
        @Field("correo") correo: String,
        @Field("contrasena") contrasena: String
    ): LoginResponse

    // ✔ RUTA CORRECTA ✔
    @Multipart
    @POST("api/usuario/upload_profile_image.php")
    suspend fun uploadProfileImage(
        @Part image: MultipartBody.Part,
        @Part("id_usuario") idUsuario: RequestBody
    ): Response<UploadResponse>

    @GET("api/receta/listar.php")
    suspend fun listarRecetas(): RecetasResponse

    @GET("api/receta/detalle.php")
    suspend fun detalleReceta(@Query("id") id: Int): RecetaDetalleResponse

    @POST("api/favorito/toggle.php")
    suspend fun toggleFavorito(@Body body: FavoritoRequest): ToggleResponse

    @GET("api/favorito/listar.php")
    suspend fun listarFavoritos(@Query("id_usuario") idUsuario: Int): RecetasResponse

    @POST("api/review/crear.php")
    suspend fun crearReview(@Body body: ReviewRequest): GenericResponse
}
