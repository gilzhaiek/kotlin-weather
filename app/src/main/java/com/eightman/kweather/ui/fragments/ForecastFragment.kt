package com.eightman.kweather.ui.fragments

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.eightman.kweather.databinding.FragmentForecastBinding
import com.eightman.kweather.network.OpenWeatherApi
import com.eightman.kweather.ui.addons.LastLocationAddon
import com.eightman.kweather.viewmodels.ForecastViewModel
import com.eightman.kweather.viewmodels.LocationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class ForecastFragment : Fragment(), LastLocationAddon {
    override val viewModelStoreOwner: ViewModelStoreOwner? get() = activity

    private val forecastViewModel: ForecastViewModel by lazy { ForecastViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentForecastBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = forecastViewModel

        lifecycle.addObserver(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelStoreOwner?.let {
            ViewModelProvider(it).get(LocationViewModel::class.java).latLon.observe(
                viewLifecycleOwner,
                Observer { latLon ->
                    try {
                        forecastViewModel.onLocationUpdated(
                            lat = latLon.lat.toDouble(),
                            lon = latLon.lon.toDouble()
                        )
                    } catch (e: NumberFormatException) {

                    }
                })
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