package nz.co.weather.ui.main

import io.reactivex.subjects.PublishSubject
import nz.co.weather.ui.base.BaseView

/**
 * View interface for the main view of the app.
 */
interface MainView : BaseView {

    /**
     * Get the auto complete publish subject.
     */
    fun getAutoCompletePublishSubject(): PublishSubject<String>

    /**
     * Show the location weather for the locationid.
     * @param locationId The location id to show weather for.
     */
    fun showLocationWeather(locationId: Int)
}
