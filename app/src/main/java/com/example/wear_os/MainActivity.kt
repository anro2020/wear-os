package com.example.wear_os

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.wear_os.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitud = 0.0
    private var longitud = 0.0

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

            textViewLatitud.text = "Latitud: \n${latitud}"
            textViewLongitud.text = "Longitud: \n${longitud}"
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
            longitud = it.longitude
            latitud = it.latitude
            //Toast.makeText(applicationContext, "${it.latitude}${it.longitude}", Toast.LENGTH_SHORT).show()
        }}
    }
}