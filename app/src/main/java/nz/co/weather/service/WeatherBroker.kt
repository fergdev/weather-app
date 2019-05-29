package nz.co.weather.service

import com.google.gson.GsonBuilder
import io.reactivex.Single
import nz.co.weather.dto.LocationWeather
import nz.co.weather.dto.LocationWrapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Implementation of weather broker using retrofit.
 */
class WeatherBroker : IWeatherBroker {

    private val service = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
        .build()
        .create(WeatherService::class.java)

    override fun getWeather(locationId: Int): Single<LocationWeather> {
        return service.getWeather(locationId.toString(), API_KEY)
    }

    override fun getLocations(query: String): Single<LocationWrapper> {
        return service.getLocations(query, API_KEY)
    }

    companion object {
        const val API_KEY = "01477d66bc501f1aeb5a65527d4d33c1"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}