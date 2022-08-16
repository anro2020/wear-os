package com.example.wear_os
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("getlocalizacion")
    fun addPost(@Body ubicacionData: UbicacionData): Call<UbicacionData>
}