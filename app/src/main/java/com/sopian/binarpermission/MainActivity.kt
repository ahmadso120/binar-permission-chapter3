package com.sopian.binarpermission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private val checkLocationPermissionButton by lazy {
        findViewById<Button>(R.id.check_location_permission_button)
    }

    private val requestPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permission is accepted", Toast.LENGTH_SHORT).show()
                getLongLat()
            } else {
                Toast.makeText(this, "Permission is declined", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadImage()

        checkLocationPermissionButton.setOnClickListener {
            requestPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun loadImage() {
        val logoIv = findViewById<ImageView>(R.id.logo)

        Glide.with(this)
            .load(IMG_URL)
            .into(logoIv)
    }

    @SuppressLint("MissingPermission")
    private fun getLongLat() {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location: Location? =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val latLongText = "Lat: ${location?.latitude} Long : ${location?.longitude}"
        Log.d(MainActivity::class.simpleName, latLongText)
        Toast.makeText(
            this,
            latLongText,
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        private const val IMG_URL =
            "https://global-uploads.webflow.com/5e70b9a791ceb781b605048c/6152ae609d46491e37aa9af9_logo_binar-academy_horizontal_magenta_bg-transparan-p-500.png"
    }
}