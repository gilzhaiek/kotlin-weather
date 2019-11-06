package com.eightman.kweather.ui.fragments

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eightman.kweather.R
import com.eightman.kweather.network.OpenWeatherApi
import com.eightman.kweather.ui.addons.LastLocationAddon
import kotlinx.android.synthetic.main.fragment_forecast.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastFragment : Fragment(), LastLocationAddon {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast, container, false)

        lifecycle.addObserver(this)

        return view
    }

    override fun onLocationUpdated(location: Location) {
        GlobalScope.launch(Dispatchers.Main) {
            val forecastResponse = withContext(Dispatchers.IO) {
                OpenWeatherApi.forecastService.forecast(
                    lat = location.latitude,
                    lon = location.longitude
                )
            }

            cityNameText.text = forecastResponse.city.name
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super<Fragment>.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super<LastLocationAddon>.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
    }
}