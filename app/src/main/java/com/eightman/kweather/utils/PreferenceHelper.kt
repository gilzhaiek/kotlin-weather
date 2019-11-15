package com.eightman.kweather.utils

import androidx.preference.PreferenceManager
import com.eightman.kweather.KWeatherApplication

object PreferenceHelper {
    fun getFromPreferences(key: String): String? = PreferenceManager
        .getDefaultSharedPreferences(KWeatherApplication.instance)?.getString(key, null)

    fun setToPreferences(key: String, value: String) {
        PreferenceManager.getDefaultSharedPreferences(KWeatherApplication.instance)
            .edit()
            .putString(key, value).apply()
    }
}