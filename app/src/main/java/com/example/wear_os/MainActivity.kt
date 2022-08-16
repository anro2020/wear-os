package com.example.wear_os

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.wear_os.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var val_latitud = 0.00
    var val_longitud = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btn_ubicacion = findViewById<Button>(R.id.btn_ubicacion)
        val textViewLatitud = findViewById<TextView>(R.id.textLatitud)
        val textViewLongitud = findViewById<TextView>(R.id.textLongitud)

        textViewLatitud.text = null
        textViewLongitud.text = null

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        btn_ubicacion.setOnClickListener{
            obtenerUbicacion()
            request(val_latitud.toString(), val_longitud.toString())
            textViewLatitud.text = "Latitud: \n${val_latitud}"
            textViewLongitud.text = "Longitud: \n${val_longitud}"
        }

    }

    private fun obtenerUbicacion(){
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener { if ( it != null) {
            val_latitud = it.longitude
            val_longitud = it.latitude
            //Toast.makeText(applicationContext, "${it.latitude}${it.longitude}", Toast.LENGTH_SHORT).show()
        }}
    }

    private fun request(latitud: String, longitud: String){
        val apiService = RestApiService()
        val ubicacionInfo = UbicacionData(
            latitud = latitud,
            longitud = longitud
        )
        apiService.nuevaUbicacion(ubicacionInfo){
            if (it?.latitud != "0" || it?.longitud != "0") {
                Toast.makeText(this, "Post enviado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fallo la peticion", Toast.LENGTH_SHORT).show()
            }
        }
    }
}