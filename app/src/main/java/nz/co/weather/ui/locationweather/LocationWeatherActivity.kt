package nz.co.weather.ui.locationweather

import android.os.Bundle
import android.view.LayoutInflater
import nz.co.weather.databinding.ActivityLocationWeatherBinding
import nz.co.weather.service.WeatherBroker
import nz.co.weather.service.scheduler.SchedulerProvider
import nz.co.weather.ui.base.BaseActivity

class LocationWeatherActivity : BaseActivity<LocationWeatherViewLogic>(), LocationWeatherView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLocationWeatherBinding.inflate(LayoutInflater.from(this))
        binding.viewLogic = viewLogic

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun initViewLogic(): LocationWeatherViewLogic {
        return LocationWeatherViewLogic(
            this,
            SchedulerProvider(),
            this,
            WeatherBroker(),
            intent.getIntExtra(LocationWeatherViewLogic.KEY_LOCATION_ID, -1)
        )
    }
}