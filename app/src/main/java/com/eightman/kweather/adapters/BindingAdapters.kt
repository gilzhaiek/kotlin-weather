package com.eightman.kweather.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eightman.kweather.R
import com.eightman.kweather.network.ForecastResponse
import com.eightman.kweather.utils.getUiDate
import java.text.SimpleDateFormat
import java.util.*

val today: String get() = System.currentTimeMillis().toInt().getUiDate()

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
    val date = (seconds * 1000).getUiDate()
    text = if (date == today) {
        resources.getString(R.string.today)
    } else {
        date
    }
}