package com.example.testevagaimgur.api

import com.example.testevagaimgur.model.GatosAPI_IMGUR
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RotasAPI {

    @GET("3/gallery/search/{sort}/{window}/{page}")
    suspend fun buscarImagem(
        @Header("Authorization") authHeader: String,
        @Path("sort") sort: String,
        @Path("window") window: String,
        @Path("page") page: String,
        @Query("q") oqueBuscar: String,
        @Query("q_type") tipoArquivo: String
    ): Response<GatosAPI_IMGUR>

}