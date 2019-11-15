package com.eightman.kweather.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eightman.kweather.R
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        intent.getStringExtra(EXTRA_LAT)?.let {
            latitudeText.setText(it)
        }
        intent.getStringExtra(EXTRA_LON)?.let {
            longitudeText.setText(it)
        }

        submitButton.setOnClickListener {
            with(Intent()) {
                putExtra(EXTRA_LAT, latitudeText.text.toString())
                putExtra(EXTRA_LON, longitudeText.text.toString())
                setResult(Activity.RESULT_OK, this)
            }

            finish()
        }
    }

    companion object {
        const val EXTRA_LAT = "EXTRA_LAT"
        const val EXTRA_LON = "EXTRA_LON"
    }
}
