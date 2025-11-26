package com.example.myapplication.network

import com.example.myapplication.model.FavoritoRequest
import com.example.myapplication.model.GenericResponse
import retrofit2.http.Body
import retrofit2.http.POST
import  com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.RecetaDetalleResponse
import com.example.myapplication.model.RecetasResponse
import com.example.myapplication.model.ReviewRequest
import com.example.myapplication.model.ToggleResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

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