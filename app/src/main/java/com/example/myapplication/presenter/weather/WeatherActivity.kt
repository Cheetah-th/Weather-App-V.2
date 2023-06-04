package com.example.myapplication.presenter.weather

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityWeatherBinding
import com.example.myapplication.datasource.model.Weather
import com.example.myapplication.presenter.temperature.TemperatureActivity
import com.example.myapplication.presenter.weather.adapter.WeatherAdapter
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by inject()
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var adapter: WeatherAdapter
    private lateinit var getSharedPreferences: SharedPreferences
    private lateinit var setSharedPreferences: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initData()
        initListener()
        onObservable()
    }

    private fun initData() {
        getSharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        setSharedPreferences = getSharedPreferences.edit()

        val cName = getSharedPreferences.getString(CITY_NAME, DEFAULT_CITY)?.lowercase()
        binding.cityNameEditText.setText(cName)
        viewModel.refreshData(cName.orEmpty())
    }

    private fun initRecyclerView(weatherItem: MutableList<Weather>) {
        adapter = WeatherAdapter(weatherItem)
        binding.weatherRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.weatherRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun initListener() {
        binding.searchCityButton.setOnClickListener {
            val cityName = binding.cityNameEditText.text.toString()
            setSharedPreferences.putString(CITY_NAME, cityName)
            setSharedPreferences.apply()
            binding.navigateToTemperatureButton.isEnabled = false
            viewModel.refreshData(cityName)
        }

        binding.navigateToTemperatureButton.setOnClickListener {
            val intent = Intent(this, TemperatureActivity::class.java)
            intent.putExtra(EXTRA_WEATHER_MODEL, viewModel.weatherModel)
            startActivity(intent)
        }
    }

    private fun onObservable() {
        viewModel.weatherData.observe(this) { data ->
            binding.apply {
                data?.let {
                    Log.d("Cheetah God", it.toString())
                    cityCodeTextView.text = it.sys.country
                    cityNameTextView.text = it.name
                    dateTimeTextView.text = epochToDateTime(it.dt.toLong())
                    errorTextView.visibility = View.GONE
                    progressbarLoading.visibility = View.GONE
                    cityCodeTextView.visibility = View.VISIBLE
                    cityNameTextView.visibility = View.VISIBLE
                    dateTimeTextView.visibility = View.VISIBLE
                    initRecyclerView(it.weather.toMutableList())
                    weatherRecyclerView.visibility = View.VISIBLE
                    navigateToTemperatureButton.isEnabled = true
                }
            }
        }

        viewModel.weatherError.observe(this) { error ->
            binding.apply {
                error?.let {
                    if (error) {
                        errorTextView.visibility = View.VISIBLE
                        progressbarLoading.visibility = View.GONE
                        cityCodeTextView.visibility = View.GONE
                        cityNameTextView.visibility = View.GONE
                        dateTimeTextView.visibility = View.GONE
                        weatherRecyclerView.visibility = View.GONE
                    } else {
                        errorTextView.visibility = View.GONE
                    }
                }
            }
        }

        viewModel.weatherLoading.observe(this) { loading ->
            binding.apply {
                loading?.let {
                    if (loading) {
                        progressbarLoading.visibility = View.VISIBLE
                        errorTextView.visibility = View.GONE
                        cityCodeTextView.visibility = View.GONE
                        cityNameTextView.visibility = View.GONE
                        dateTimeTextView.visibility = View.GONE
                        weatherRecyclerView.visibility = View.GONE
                    } else {
                        progressbarLoading.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun epochToDateTime(epoch: Long?): String {
        var dateTime = ""
        epoch?.let {
            val date = Date(epoch.times(1000))
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateTime = format.format(date)
        }
        return dateTime
    }

    companion object {
        const val CITY_NAME = "CITY_NAME"
        const val DEFAULT_CITY = "BANGKOK"
        const val EXTRA_WEATHER_MODEL = "EXTRA_WEATHER_MODEL"
    }
}