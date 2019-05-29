package nz.co.weather.ui.main

import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import nz.co.weather.BaseUnitTest
import nz.co.weather.dto.Location
import nz.co.weather.dto.LocationWrapper
import nz.co.weather.service.IWeatherBroker
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.util.concurrent.TimeUnit

class MainViewLogicTest : BaseUnitTest() {

    @Mock
    private lateinit var view: MainView
    @Mock
    private lateinit var weatherBroker: IWeatherBroker
    private lateinit var mainViewLogic: MainViewLogic

    @Before
    override fun setup() {
        super.setup()
        mainViewLogic = MainViewLogic(schedulerProvider, view, weatherBroker)
    }

    @Test
    fun `on resume - subscribes to text change`() {
        //given
        val autoCompletePublishSubject = PublishSubject.create<String>()
        `when`(view.getAutoCompletePublishSubject()).thenReturn(autoCompletePublishSubject)

        //when
        mainViewLogic.onResume()

        //then
        verify(view).getAutoCompletePublishSubject()
    }

    @Test
    fun `publish subject - next value invokes service`() {
        //given
        val autoCompletePublishSubject = PublishSubject.create<String>()

        `when`(view.getAutoCompletePublishSubject()).thenReturn(autoCompletePublishSubject)
        `when`(weatherBroker.getLocations("hello")).thenReturn(
            Single.just(LocationWrapper(listOf(Location("abc", 1, null))))
        )

        mainViewLogic.onResume()
        ioTestScheduler.triggerActions()

        //when
        autoCompletePublishSubject.onNext("hello")
        computationTestScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        //then
        verify(weatherBroker).getLocations("hello")
    }

    @Test
    fun `valid location - updates view`() {
        //given
        val autoCompletePublishSubject = PublishSubject.create<String>()

        `when`(view.getAutoCompletePublishSubject()).thenReturn(autoCompletePublishSubject)
        `when`(weatherBroker.getLocations("hello")).thenReturn(
            Single.just(LocationWrapper(listOf(Location("abc", 1, null))))
        )

        mainViewLogic.onResume()
        ioTestScheduler.triggerActions()

        //when
        autoCompletePublishSubject.onNext("hello")
        computationTestScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        ioTestScheduler.triggerActions()
        uiTestScheduler.triggerActions()
        computationTestScheduler.triggerActions()

        //then
        assertThat(mainViewLogic.locations.size, `is`(1))
        assertThat(mainViewLogic.showWelcomeMessage.get(), `is`(false))
    }

    @Test
    fun `on location selected - does navigation`() {
        //given
        val location = Location("abc", 1, null)
        //when

        mainViewLogic.onLocationSelected(location)

        //then
        verify(view).showLocationWeather(1)
    }
}

