package com.eightman.kweather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eightman.kweather.network.ForecastResponse
import com.eightman.kweather.network.OpenWeatherApi
import kotlinx.coroutines.*


class ForecastViewModel : ViewModel() {
    private var currentJob: Job? = null

    private val forecastResponse = MutableLiveData<ForecastResponse?>()

    init {
        onLocationUpdated(DEFAULT_LAT, DEFAULT_LON)
    }

    fun getForecastResponse(): LiveData<ForecastResponse?> = forecastResponse

    fun onLocationUpdated(lat: Double, lon: Double) {
        currentJob?.cancel()
        currentJob = GlobalScope.launch(Dispatchers.Main) {
            forecastResponse.value = withContext(Dispatchers.IO) {
                OpenWeatherApi.forecastService.forecast(
                    lat = lat,
                    lon = lon
                )
            }
        }
    }

    companion object {
        private const val DEFAULT_LAT = 49.2497
        private const val DEFAULT_LON = -123.1194
    }
}

