package com.eightman.kweather.network

import com.eightman.kweather.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
private const val API_KEY = "e037ac6f401108820da4073ecd447eaa"

interface ForecastApiService {
    @GET("forecast/{freq}?appid=$API_KEY")
    suspend fun forecast(
        @Path("freq") freq: String = "daily",
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int = 10
    ): ForecastResponse
}

object OpenWeatherApi {
    private val retrofit =
        with(Retrofit.Builder()) {
            client(
                OkHttpClient.Builder().apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(HttpLoggingInterceptor().apply {
                            this.level = HttpLoggingInterceptor.Level.BODY
                        })
                    }
                }.build()
            )
            addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            baseUrl(BASE_URL)
            build()
        }

    val forecastService: ForecastApiService by lazy {
        retrofit.create(ForecastApiService::class.java)
    }
}