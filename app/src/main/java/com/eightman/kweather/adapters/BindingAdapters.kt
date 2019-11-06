package com.eightman.kweather.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.eightman.kweather.network.ForecastResponse

@BindingAdapter("showOnlyWhenEmpty")
fun View.showOnlyWhenEmpty(data: ForecastResponse?) {
    visibility = when {
        data == null || data.forecast.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}
