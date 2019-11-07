package com.eightman.kweather.network

import com.eightman.kweather.R
import com.eightman.kweather.utils.getUiTime
import com.squareup.moshi.Json

data class Coord(val lon: Double, val lat: Double)

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coord
) {
    val inImperial: Boolean get() = country == "US"
}

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) {
    val iconRes: Int get() = getIconRes(icon)

    companion object {
        val placeholder: Weather by lazy { Weather(0, "", "", "") }
    }
}

data class Temp(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Forecast(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>,
    val speed: Double,
    val deg: Int,
    val clouds: Int
) {
    val weatherFirst: Weather get() = weather.firstOrNull() ?: Weather.placeholder
    val humidityStr: String get() = "$humidity%"
    val sunriseSunsetStr: String get() = "${sunrise.getUiTime()}/${sunset.getUiTime()}"
}

data class ForecastResponse(
    @Json(name = "city") val city: City,
    @Json(name = "list") val forecast: List<Forecast>
)

fun getIconRes(openWeatherIcon: String): Int = when (openWeatherIcon) {
    "01d" -> R.drawable.clear_sky_d
    "01n" -> R.drawable.clear_sky_n
    "02d" -> R.drawable.few_clouds_d
    "02n" -> R.drawable.few_clouds_n
    "03d" -> R.drawable.scattered_clouds
    "03n" -> R.drawable.scattered_clouds
    "04d" -> R.drawable.broken_clouds
    "04n" -> R.drawable.broken_clouds
    "09d" -> R.drawable.shower_rain
    "09n" -> R.drawable.shower_rain
    "10d" -> R.drawable.rain
    "10n" -> R.drawable.rain
    "11d" -> R.drawable.thunderstorm
    "11n" -> R.drawable.thunderstorm
    "13d" -> R.drawable.snow
    "13n" -> R.drawable.snow
    "50d" -> R.drawable.mist
    "50n" -> R.drawable.mist
    else -> R.drawable.weather_na
}