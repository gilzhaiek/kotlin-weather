package com.eightman.kweather

import android.app.Application

class KWeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: KWeatherApplication
            private set
    }
}