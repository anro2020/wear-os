package com.example.wear_os

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    fun nuevaUbicacion(ubicacionData: UbicacionData , onResult: (UbicacionData?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addPost(ubicacionData).enqueue(
            object : Callback<UbicacionData> {
                override fun onFailure(call: Call<UbicacionData>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<UbicacionData>, response: Response<UbicacionData>) {
                    val postAgregado = response.body()
                    onResult(postAgregado)
                }
            }
        )
    }
}