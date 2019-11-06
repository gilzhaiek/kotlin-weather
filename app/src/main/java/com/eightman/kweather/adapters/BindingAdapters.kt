package com.eightman.kweather.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eightman.kweather.R
import com.eightman.kweather.network.ForecastResponse
import java.text.SimpleDateFormat
import java.util.*

val today: String get() = getUiDate(System.currentTimeMillis())

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
    val date = getUiDate(seconds * 1000L)
    text = if (date == today) {
        resources.getString(R.string.today)
    } else {
        date
    }
}

fun getUiDate(millis: Long): String =
    SimpleDateFormat("MMM dd, yyyy", Locale.US).let { simpleDateFormat ->
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return simpleDateFormat.format(Date(millis))
    }