package nz.co.weather.ui.locationweather

import android.content.Context
import io.reactivex.Single
import nz.co.weather.BaseUnitTest
import nz.co.weather.R
import nz.co.weather.dto.LocationWeather
import nz.co.weather.dto.Main
import nz.co.weather.dto.Sys
import nz.co.weather.dto.Weather
import nz.co.weather.service.IWeatherBroker
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class LocationWeatherViewLogicTest : BaseUnitTest() {

    @Mock
    private lateinit var context: Context
    @Mock
    private lateinit var view: LocationWeatherView
    @Mock
    private lateinit var weatherBroker: IWeatherBroker
    private lateinit var viewLogic: LocationWeatherViewLogic

    @Before
    override fun setup() {
        super.setup()

        `when`(context.getString(R.string.comma_separated)).thenReturn("%s, %s")
        `when`(context.getString(R.string.format_celcius)).thenReturn("%.2f celsius")

        viewLogic = LocationWeatherViewLogic(
            context, schedulerProvider, view, weatherBroker, 1
        )
    }

    @Test
    fun `on resume - shows loading`() {
        //given
        `when`(weatherBroker.getWeather(1)).thenReturn(
            Single.just(
                LocationWeather(
                    1, "name", Main(1.0, 2.0, 3.0, 4.0, 5.0)
                    , listOf(Weather("main", "description", "icon")),
                    Sys("nz")
                )
            )
        )


        //when
        viewLogic.onResume()

        //then
        assertThat(
            viewLogic.loading.get(), `is`(true)
        )
    }

    @Test
    fun `on data - shows data`() {
        //given
        `when`(weatherBroker.getWeather(1)).thenReturn(
            Single.just(
                LocationWeather(
                    1, "name", Main(285.0, 2.0, 3.0, 4.0, 5.0)
                    , listOf(Weather("main", "description", "icon")),
                    Sys("nz")
                )
            )
        )

        viewLogic.onResume()

        //when
        ioTestScheduler.triggerActions()
        uiTestScheduler.triggerActions()

        //then
        assertThat(viewLogic.loading.get(), `is`(false))
        assertThat(viewLogic.name.get(), `is`("name, nz"))
        assertThat(viewLogic.temperature.get(), `is`("11.85 celsius"))
        assertThat(viewLogic.weather.get(), `is`("description"))
        assertThat(viewLogic.weatherIconUrl.get(), `is`("https://openweathermap.org/img/w/icon.png"))
    }
}
