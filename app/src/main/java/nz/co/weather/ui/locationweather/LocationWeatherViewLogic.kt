package nz.co.weather.ui.locationweather

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import nz.co.weather.R
import nz.co.weather.ZERO_KALVIN_CELSIUS
import nz.co.weather.dto.LocationWeather
import nz.co.weather.service.IWeatherBroker
import nz.co.weather.service.ImageUrlResolver
import nz.co.weather.service.scheduler.ISchedulerProvider
import nz.co.weather.ui.base.BaseViewLogic

/**
 * View Logic to display location weather information.
 */
class LocationWeatherViewLogic(
    private val context: Context,
    private val schedulerProvider: ISchedulerProvider,
    view: LocationWeatherView,
    private val weatherBroker: IWeatherBroker,
    private val locationId: Int
) :
    BaseViewLogic<LocationWeatherView>(view) {

    val loading = ObservableBoolean(false)
    val name = ObservableField<String>("")
    val weather = ObservableField<String>("")
    val temperature = ObservableField<String>("")
    val weatherIconUrl = ObservableField<String>("")

    override fun onResume() {
        super.onResume()
        loading.set(true)
        bindToLifeCycle(
            weatherBroker.getWeather(locationId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    onWeatherData(it)
                }, { t: Throwable ->
                    view.showError(t.localizedMessage)
                })
        )
    }

    private fun onWeatherData(data: LocationWeather) {
        loading.set(false)
        name.set(context.getString(R.string.comma_separated).format(data.name, data.sys.country))
        weather.set(data.weathers.first().description)
        temperature.set(context.getString(R.string.format_celcius).format(data.main.temp + ZERO_KALVIN_CELSIUS))
        weatherIconUrl.set(ImageUrlResolver.resolveImageUrl(data.weathers.first().icon))
    }

    companion object {
        const val KEY_LOCATION_ID = "LOCATION_ID"
    }
}