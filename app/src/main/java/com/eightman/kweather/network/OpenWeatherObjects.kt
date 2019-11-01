package com.eightman.kweather.network

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
)

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
)

data class ForecastResponse(
    @Json(name = "city") val city: City,
    @Json(name = "list") val forecast: List<Forecast>
)