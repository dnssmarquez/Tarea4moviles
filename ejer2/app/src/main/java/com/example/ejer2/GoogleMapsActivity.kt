package com.example.ejer2

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tarea4_2.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class GoogleMapsActivity : AppCompatActivity() {

    private lateinit var webViewGoogleMaps: WebView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        webViewGoogleMaps = findViewById(R.id.webViewGoogleMaps)
        val webSettings: WebSettings = webViewGoogleMaps.settings
        webSettings.javaScriptEnabled = true

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            obtenerUbicacionYMostrarMapa()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacionYMostrarMapa()
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
                cargarMapaEstatico()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacionYMostrarMapa() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    cargarMapaConCoordenadas(location.latitude, location.longitude)
                } else {
                    Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                    cargarMapaEstatico()
                }
            }
    }
    private fun cargarMapaConCoordenadas(latitude: Double, longitude: Double) {
        val googleMapsUrl =
            "https://www.google.com/maps?q=19.368598,-99.152851&z=15" // z is the zoom level
        webViewGoogleMaps.loadUrl(googleMapsUrl)
    }

    private fun cargarMapaEstatico() {
        webViewGoogleMaps.loadUrl("https://www.google.com/maps")
    }
}