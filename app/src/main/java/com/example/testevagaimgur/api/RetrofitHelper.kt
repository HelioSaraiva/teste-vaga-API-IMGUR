package com.example.testevagaimgur.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private const val BASE_URL = "https://api.imgur.com/"
    const val ACCESS_TOKEN = "Bearer 3a3c3f37f203dd13fdf2fe9b3daff3d9e762f635"
    const val sort = "top"
    const val window = "week"
    const val page = "0"
    const val oqueBuscar = "cats"
    const val tipoArquivo = "jpg"






    val retrofit = Retrofit.Builder()
        .baseUrl( BASE_URL )
        .addConverterFactory( GsonConverterFactory.create() )
        //.client(okHttpClient)
        .build()

    val imagemAPI = retrofit.create( RotasAPI::class.java )


}