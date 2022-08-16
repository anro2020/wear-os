package com.example.wear_os

import com.google.gson.annotations.SerializedName

data class UbicacionData(
    @SerializedName("latitud") val latitud: String?,
    @SerializedName("longitud") val longitud: String?
)
