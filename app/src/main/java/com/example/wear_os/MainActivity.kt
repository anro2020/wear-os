package com.example.wear_os

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.wear_os.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : Activity(), LocationListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2
    lateinit var btn_ubicacion : Button
    lateinit var textViewLatitud : TextView
    lateinit var textViewLongitud : TextView
    /*private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var val_latitud = 0.00
    var val_longitud = 0.00*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btn_ubicacion = findViewById(R.id.btn_ubicacion)
        textViewLatitud = findViewById(R.id.textLatitud)
        textViewLongitud = findViewById(R.id.textLongitud)

        textViewLatitud.text = ""
        textViewLongitud.text = ""

        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        btn_ubicacion.setOnClickListener{
            getLocation()
           /* obtenerUbicacion()
            request(val_latitud.toString(), val_longitud.toString())
            textViewLatitud.text = "Latitud: \n${val_latitud}"
            textViewLongitud.text = "Longitud: \n${val_longitud}"*/
        }

    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1f, this)
    }

    override fun onLocationChanged(location: Location) {
        textViewLatitud.text = "Latitude: " + location.latitude
        textViewLongitud.text = "Longitude: " + location.longitude
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*private fun obtenerUbicacion(){
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
    }*/
}