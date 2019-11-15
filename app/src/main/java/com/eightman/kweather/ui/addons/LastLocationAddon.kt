package com.eightman.kweather.ui.addons

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.eightman.kweather.KWeatherApplication.Companion.instance
import com.eightman.kweather.viewmodels.LocationViewModel
import com.google.android.gms.location.*

interface LastLocationAddon : LifecycleObserver {
    val viewModelStoreOwner: ViewModelStoreOwner?

    fun getActivity(): Activity?

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
        val activity = getActivity() ?: return

        with(LocationServices.getFusedLocationProviderClient(activity)) {
            lastLocation.addOnSuccessListener { location ->
                when (location) {
                    null -> startLocationUpdates(this)
                    else -> updateLocation(location)
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
                        updateLocation(it)
                    }
                }
            },
            null
        )
    }

    private fun updateLocation(location: Location) {
        viewModelStoreOwner?.let {
            val viewModel = ViewModelProvider(it).get(LocationViewModel::class.java)
            if(viewModel.latLon.value == null) {
                viewModel.latLon.postValue(
                    LocationViewModel.LatLon(
                        location.latitude,
                        location.longitude
                    )
                )
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(LOCATION_PERMISSION), LOCATION_PERMISSION_REQUEST)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 1
        private const val LOCATION_PERMISSION = "android.permission.ACCESS_FINE_LOCATION"
    }
}