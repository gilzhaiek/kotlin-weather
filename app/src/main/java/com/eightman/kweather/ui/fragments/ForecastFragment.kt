package com.eightman.kweather.ui.fragments

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eightman.kweather.adapters.ForecastListAdapter
import com.eightman.kweather.databinding.FragmentForecastBinding
import com.eightman.kweather.network.OpenWeatherApi
import com.eightman.kweather.ui.addons.LastLocationAddon
import com.eightman.kweather.viewmodels.ForecastViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class ForecastFragment : Fragment(), LastLocationAddon {
    private val forecastViewModel: ForecastViewModel by lazy { ForecastViewModel() }
    private val forecastListAdapter: ForecastListAdapter by lazy { ForecastListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentForecastBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = forecastViewModel
        binding.forecastList.adapter = forecastListAdapter

        lifecycle.addObserver(this)

        return binding.root
    }

    override fun onLocationUpdated(location: Location) {
        super.onLocationUpdated(location)
        forecastViewModel.onLocationUpdated(location)
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