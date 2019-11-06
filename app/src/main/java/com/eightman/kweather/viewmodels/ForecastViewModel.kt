package com.eightman.kweather.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eightman.kweather.network.ForecastResponse
import com.eightman.kweather.network.OpenWeatherApi
import kotlinx.coroutines.*

class ForecastViewModel : ViewModel() {
    private var currentJob: Job? = null

    val forecastResponse = MutableLiveData<ForecastResponse?>()

    fun getForecastResponse(): LiveData<ForecastResponse?> = forecastResponse

    fun onLocationUpdated(location: Location) {
        currentJob?.cancel()
        currentJob = GlobalScope.launch(Dispatchers.Main) {
            forecastResponse.value = withContext(Dispatchers.IO) {
                OpenWeatherApi.forecastService.forecast(
                    lat = location.latitude,
                    lon = location.longitude
                )
            }
        }
    }
}