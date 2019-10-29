package com.eightman.kweather.network

import com.squareup.moshi.Json

data class Coord(val lon: Double, val lat: Double)

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coord
)

data class ForecastResponse(
    @Json(name = "city") val city: City
)