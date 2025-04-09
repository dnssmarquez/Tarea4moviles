package com.example.tarea4ejercicio1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asegúrate de que este es el layout correcto

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/map.html")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Solicita permisos si no están dados
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

        // Obtiene la ubicación
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude
                // Llamada para actualizar la ubicación en el WebView usando JavaScript
                webView.evaluateJavascript("updateLocation($lat, $lon);", null)
            } else {
                // Manejo de error si la ubicación es nula
                Toast.makeText(this, "Ubicación no disponible", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para manejar el resultado de la solicitud de permisos
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si el permiso fue concedido, obtenemos la ubicación
                getLastLocation()
            } else {
                // Si el permiso fue denegado, mostramos un mensaje
                Toast.makeText(this, "Permiso de ubicación necesario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para obtener la ubicación cuando se ha concedido el permiso
    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude
                // Llamada para actualizar la ubicación en el WebView usando JavaScript
                webView.evaluateJavascript("updateLocation($lat, $lon);", null)
            } else {
                // Manejo de error si la ubicación es nula
                Toast.makeText(this, "Ubicación no disponible", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
