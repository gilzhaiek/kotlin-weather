package com.eightman.kweather.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.getUiDate(): String =
    SimpleDateFormat("MMM dd, yyyy", Locale.US).let { simpleDateFormat ->
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return simpleDateFormat.format(Date(this))
    }

fun Int.getUiTime(): String =
    SimpleDateFormat("h:mm a", Locale.US).let { simpleDateFormat ->
        return simpleDateFormat.format(Date(this * 1000L))
    }


fun Double.kelvinToC(): Double = this - 273.15
fun Double.kelvinToF(): Double = this.kelvinToC() * 1.8 + 32.0
fun Double.mpsToKMH(): Double = this * 3.6
fun Double.mpsToMPH(): Double = this * 2.23694
fun Double.round(decimals: Int = 1): Double = "%.${decimals}f".format(this).toDouble()
