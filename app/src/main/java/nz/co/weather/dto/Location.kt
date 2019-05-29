package nz.co.weather.dto

import com.google.gson.annotations.SerializedName

/**
 * File for the location endpoint dtos.
 */
data class LocationWrapper(
    @SerializedName("list") val locationList: List<Location>
)

data class Location(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("sys") val sys: Sys?
)

data class Sys(
    @SerializedName("country") val country: String
)

