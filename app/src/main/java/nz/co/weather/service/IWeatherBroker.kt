package nz.co.weather.service

import io.reactivex.Single
import nz.co.weather.dto.LocationWeather
import nz.co.weather.dto.LocationWrapper

/**
 * Broker for weather information.
 */
interface IWeatherBroker {
    /**
     * Get weather information for a given location id.
     * @param locationId The id of the location to get weather for.
     */
    fun getWeather(locationId: Int): Single<LocationWeather>

    /**
     * Search for locations based of given query.
     * @param query The location query to find.
     */
    fun getLocations(query: String): Single<LocationWrapper>
}