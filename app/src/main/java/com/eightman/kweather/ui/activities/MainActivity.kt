package com.eightman.kweather.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eightman.kweather.R
import com.eightman.kweather.utils.PreferenceHelper
import com.eightman.kweather.viewmodels.LocationViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val locationViewModel by lazy { ViewModelProvider(this).get(LocationViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addLocationButton.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            PreferenceHelper.getFromPreferences(PREF_LAST_LAT)?.let {
                intent.putExtra(LocationActivity.EXTRA_LAT, it)
            }
            PreferenceHelper.getFromPreferences(PREF_LAST_LON)?.let {
                intent.putExtra(LocationActivity.EXTRA_LON, it)
            }
            startActivityForResult(intent, LOCATION_ACTIVITY_RESULT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCATION_ACTIVITY_RESULT && resultCode == RESULT_OK && data != null) {
            updateLatLon(
                data.getStringExtra(LocationActivity.EXTRA_LAT)!!,
                data.getStringExtra(LocationActivity.EXTRA_LON)!!
            )
        }
    }

    private fun updateLatLon(lat: String, lon: String) {
        PreferenceHelper.setToPreferences(PREF_LAST_LAT, lat)
        PreferenceHelper.setToPreferences(PREF_LAST_LON, lon)
        locationViewModel.latLon.postValue(LocationViewModel.LatLon(lat, lon))
    }

    companion object {
        const val PREF_LAST_LAT = "pref.last_lat"
        const val PREF_LAST_LON = "pref.last_lon"
        const val LOCATION_ACTIVITY_RESULT = 11
    }
}
