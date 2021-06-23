package com.egorov.vetfind.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.egorov.vetfind.R
import com.egorov.vetfind.databinding.ActivityMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mapView = binding.root

        val latitude: String? = intent.getStringExtra("latitude")
        val longitude: String? = intent.getStringExtra("longitude")

        if (latitude != null && longitude != null) {
            val point = Point(latitude.toDouble(), longitude.toDouble())
            addPlacemark(point)
            moveCamera(point)
        }
    }

    private fun moveCamera(location: Point) {
        mapView.map.move(
            CameraPosition(location, 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 2F),
            null
        )
    }

    private fun addPlacemark(location: Point) {
        // Remove previous placemarks
        mapView.map.mapObjects.clear()
        // Add new placemark
        mapView.map.mapObjects.addPlacemark(
            location, ImageProvider.fromResource(
                this,
                R.drawable.placemark
            )
        )
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }
}