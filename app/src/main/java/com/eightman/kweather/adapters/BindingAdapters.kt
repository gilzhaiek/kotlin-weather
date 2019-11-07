package com.eightman.kweather.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eightman.kweather.R
import com.eightman.kweather.network.ForecastResponse
import com.eightman.kweather.utils.*

val today: String get() = System.currentTimeMillis().getUiDate()

@BindingAdapter("showOnlyWhenEmpty")
fun View.showOnlyWhenEmpty(data: ForecastResponse?) {
    visibility = when {
        data == null || data.forecast.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("forecastResponse")
fun bindRecyclerView(recyclerView: RecyclerView, data: ForecastResponse?) {
    data ?: return

    (recyclerView.adapter as ForecastListAdapter).submitList(data.forecast) {
        recyclerView.scrollToPosition(0)
    }
}

@BindingAdapter("secToDate")
fun TextView.day(seconds: Int) {
    val date = (seconds * 1000L).getUiDate()
    text = if (date == today) {
        resources.getString(R.string.today)
    } else {
        date
    }
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
    imageView.setColorFilter(
        imageView.resources.getColor(
            when (resource) {
                R.drawable.clear_sky_d -> R.color.colorSun
                R.drawable.clear_sky_n -> R.color.colorMoon
                else -> R.color.colorWeather
            }, null
        )
    )
}

@BindingAdapter("bind:kelvin", "bind:inImperial")
fun TextView.degrees(kelvin: Double, inImperial: Boolean) {
    text =
        if (inImperial) (kelvin.kelvinToF().round().toString())
        else (kelvin.kelvinToC().round().toString())
}

@BindingAdapter("bind:speed", "bind:inImperial")
fun TextView.speed(speed: Double, inImperial: Boolean) {
    text =
        if (inImperial) (speed.mpsToMPH().round().toString())
        else (speed.mpsToKMH().round().toString())
}