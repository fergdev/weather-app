package nz.co.weather.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import androidx.appcompat.widget.SearchView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import nz.co.weather.R
import nz.co.weather.databinding.ActivityMainBinding
import nz.co.weather.dto.Location
import nz.co.weather.service.scheduler.SchedulerProvider
import nz.co.weather.service.WeatherBroker
import nz.co.weather.ui.base.BaseActivity
import nz.co.weather.ui.locationweather.LocationWeatherActivity
import nz.co.weather.ui.locationweather.LocationWeatherViewLogic
import nz.co.weather.ui.util.LocationAdapter

class MainActivity : BaseActivity<MainViewLogic>(), MainView {

    override fun showLocationWeather(locationId: Int) {
        val intent = Intent(this, LocationWeatherActivity::class.java)
        intent.putExtra(LocationWeatherViewLogic.KEY_LOCATION_ID, locationId)
        startActivity(intent)
    }

    override fun getAutoCompletePublishSubject(): PublishSubject<String> {
        return subject
    }

    private val subject = PublishSubject.create<String>()

    override fun initViewLogic(): MainViewLogic {
        return MainViewLogic(SchedulerProvider(), this, WeatherBroker())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        binding.viewLogic = viewLogic
        val locationAdapter = LocationAdapter()
        locationAdapter.setLocationSelectedListener(object : LocationAdapter.LocationSelectedListener {
            override fun onLocationSelected(location: Location) {
                viewLogic.onLocationSelected(location)
            }
        })
        binding.recyclerView.adapter = locationAdapter
        setContentView(binding.root)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.searchBar)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_locations)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) subject.onNext(newText)
                return true
            }
        })
        return true
    }
}
