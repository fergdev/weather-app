package nz.co.weather.dto

import com.google.gson.annotations.SerializedName

/**
 * File for Location Weather related dto's.
 */

data class LocationWeather(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weathers: List<Weather>,
    @SerializedName("sys") val sys: Sys
)

data class Weather(
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double
)
