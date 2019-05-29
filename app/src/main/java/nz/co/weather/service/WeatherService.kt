package nz.co.weather.service

import io.reactivex.Single
import nz.co.weather.dto.LocationWeather
import nz.co.weather.dto.LocationWrapper
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Retrofit Service interface for weather information.
 */
interface WeatherService {

    /**
     * Get the weather information for the given location id.
     *
     * @param locationId The id of the location to search for.
     * @param appid The api key for the weather service.
     */
    @GET("weather")
    fun getWeather(@Query("id") locationId: String, @Query("appid") appid: String): Single<LocationWeather>

    /*
    * Search for locations that have weather data.
    *
    * @param query The query to search for.
    * @param appid The api key for the weather service.
    */
    @GET("find")
    fun getLocations(@Query("q") query: String, @Query("appid") appid: String): Single<LocationWrapper>
}