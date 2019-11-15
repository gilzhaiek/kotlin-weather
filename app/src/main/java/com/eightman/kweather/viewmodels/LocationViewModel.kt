package com.eightman.kweather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    class LatLon(val lat: String, val lon: String) {
        constructor(lat: Double, lon: Double) : this(lat.toString(), lon.toString())
    }

    val latLon = MutableLiveData<LatLon>()
}