package nz.co.weather.ui.main

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import nz.co.weather.dto.Location
import nz.co.weather.service.scheduler.ISchedulerProvider
import nz.co.weather.service.IWeatherBroker
import nz.co.weather.ui.base.BaseViewLogic
import java.util.concurrent.TimeUnit

class MainViewLogic(
    private val schedulerProvider: ISchedulerProvider,
    view: MainView,
    private val weatherBroker: IWeatherBroker
) : BaseViewLogic<MainView>(view) {

    val showWelcomeMessage = ObservableBoolean(true)
    val locations = ObservableArrayList<Location>()
    private var searchString = ""

    override fun onResume() {
        bindToLifeCycle(view.getAutoCompletePublishSubject()
            .debounce(300, TimeUnit.MILLISECONDS, schedulerProvider.computation())
            .distinctUntilChanged()
            .filter { it.length >= 3 }
            .subscribeOn(schedulerProvider.io())
            .subscribe {
                onNewSearchString(it)
            })
    }

    private fun onNewSearchString(searchString: String) {
        this.searchString = searchString
        bindToLifeCycle(
            weatherBroker.getLocations(searchString)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        locations.clear()
                        locations.addAll(it.locationList)
                        showWelcomeMessage.set(false)
                    },
                    { view.showError(it.localizedMessage) }
                ))
    }

    fun onLocationSelected(location: Location) {
        if (location.id != -1) view.showLocationWeather(location.id)
    }
}