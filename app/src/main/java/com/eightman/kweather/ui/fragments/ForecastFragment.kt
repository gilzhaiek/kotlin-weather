package com.eightman.kweather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eightman.kweather.R
import com.eightman.kweather.network.OpenWeatherApi
import kotlinx.android.synthetic.main.fragment_forecast.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_forecast, container, false)

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main) {
            val forecastResponse = withContext(Dispatchers.IO) {
                OpenWeatherApi.forecastService.forecast(lat = 49.2827, lon = -123.1207)
            }

            cityName.text = forecastResponse.city.name
        }
    }
}