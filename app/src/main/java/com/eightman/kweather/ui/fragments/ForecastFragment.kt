package com.eightman.kweather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eightman.kweather.adapters.ForecastListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.eightman.kweather.databinding.FragmentForecastBinding
import com.eightman.kweather.ui.addons.LastLocationAddon
import com.eightman.kweather.viewmodels.ForecastViewModel
import com.eightman.kweather.viewmodels.LocationViewModel

class ForecastFragment : Fragment(), LastLocationAddon {
    override val viewModelStoreOwner: ViewModelStoreOwner? get() = activity

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