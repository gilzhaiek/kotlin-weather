package com.eightman.kweather.ui.addons

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.eightman.kweather.KWeatherApplication.Companion.instance
import com.eightman.kweather.db.AppDatabase
import com.eightman.kweather.db.entities.LocationEntity
import com.google.android.gms.location.*

interface LastLocationAddon : LifecycleObserver {
    fun getContext(): Context?

    fun onLocationUpdated(location: Location) {
        AppDatabase.getDatabase().locationDao().insert(
            LocationEntity(
                timestamp = (location.time / 1000).toInt(),
                latitude = location.latitude,
                longitude = location.longitude
            )
        )
    }

    fun requestPermissions(permissions: Array<String>, requestCode: Int) // Fragment class

    fun hasOrRequestLocationPermission(): Boolean? =
        if (ContextCompat.checkSelfPermission(
                instance, LOCATION_PERMISSION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
            null
        } else {
            true
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun requestLastLocationOrStartLocationUpdates() {
        hasOrRequestLocationPermission() ?: return
        val context = getContext() ?: return

        with(LocationServices.getFusedLocationProviderClient(context)) {
            lastLocation.addOnSuccessListener { location ->
                when (location) {
                    null -> startLocationUpdates(this)
                    else -> onLocationUpdated(location)
                }
            }
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            requestLastLocationOrStartLocationUpdates()
        }
    }

    private fun startLocationUpdates(fusedLocationClient: FusedLocationProviderClient) {
        hasOrRequestLocationPermission() ?: return

        fusedLocationClient.requestLocationUpdates(
            LocationRequest().setPriority(LocationRequest.PRIORITY_LOW_POWER),
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.lastLocation?.let {
                        onLocationUpdated(it)
                    }
                }
            },
            null
        )
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(LOCATION_PERMISSION), LOCATION_PERMISSION_REQUEST)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 1
        private const val LOCATION_PERMISSION = "android.permission.ACCESS_FINE_LOCATION"
    }
}