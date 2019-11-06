package com.eightman.kweather.utils

import java.text.SimpleDateFormat
import java.util.*

fun Int.getUiDate(): String =
    SimpleDateFormat("MMM dd, yyyy", Locale.US).let { simpleDateFormat ->
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return simpleDateFormat.format(Date(this.toLong()))
    }