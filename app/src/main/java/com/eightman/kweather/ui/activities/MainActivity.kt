package com.eightman.kweather.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.eightman.kweather.R
import com.eightman.kweather.db.AppDatabase
import com.eightman.kweather.db.dao.LocationDao

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppDatabase.getDatabase().locationDao().getLocationHistory()
            .observe(this, Observer { locationEntityList ->
                locationEntityList.forEach { locationEntity ->
                    Log.d(
                        "LocationEntity",
                        "${locationEntity.timestamp} ${locationEntity.latitude} ${locationEntity.longitude}"
                    )
                }
            })
    }
}
