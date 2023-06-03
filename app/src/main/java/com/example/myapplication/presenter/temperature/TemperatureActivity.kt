package com.example.myapplication.presenter.temperature

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityTemperatureBinding
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TemperatureActivity : AppCompatActivity() {

    private val viewModel: TemperatureViewModel by inject()
    private lateinit var binding: ActivityTemperatureBinding
    private lateinit var getSharedPreferences: SharedPreferences
    private lateinit var setSharedPreferences: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemperatureBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initData()
        initView()
        initListener()
        onObservable()
    }

    private fun initData() {
        getSharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        setSharedPreferences = getSharedPreferences.edit()

        val cName = getSharedPreferences.getString(CITY_NAME, DEFAULT_CITY)?.lowercase()
        binding.cityNameEditText.setText(cName)

        viewModel.weatherModel = intent.getParcelableExtra(EXTRA_WEATHER_MODEL)
    }

    private fun initView() {
        binding.apply {
            cityCodeTextView.text = viewModel.weatherModel?.sys?.country
            cityNameTextView.text = viewModel.weatherModel?.name
            dateTimeTextView.text = epochToDateTime(viewModel.weatherModel?.dt?.toLong())
            errorTextView.visibility = View.GONE
            progressbarLoading.visibility = View.GONE
            cityCodeTextView.visibility = View.VISIBLE
            cityNameTextView.visibility = View.VISIBLE
            dateTimeTextView.visibility = View.VISIBLE
            temperatureView.weatherImage.setImageResource(
                viewModel.weatherModel?.weather?.first()?.icon?.image
                    ?: R.drawable.ic_holder
            )
            temperatureView.temperatureCelsiusValue.text = "${viewModel.weatherModel?.main?.temp}°C"
            temperatureView.temperatureFahrenheitValue.text =
                "${celsiusToFahrenheit(viewModel.weatherModel?.main?.temp)}°F"
            temperatureView.temperatureFeelLikeCelsiusValue.text =
                "${viewModel.weatherModel?.main?.feels_like}°C"
            temperatureView.temperatureFeelLikeFahrenheitValue.text =
                "${celsiusToFahrenheit(viewModel.weatherModel?.main?.feels_like)}°F"
            temperatureView.temperatureMaxCelsiusValue.text =
                "${viewModel.weatherModel?.main?.temp_max}°C"
            temperatureView.temperatureMaxFahrenheitValue.text =
                "${celsiusToFahrenheit(viewModel.weatherModel?.main?.temp_max)}°F"
            temperatureView.temperatureMinCelsiusValue.text =
                "${viewModel.weatherModel?.main?.temp_min}°C"
            temperatureView.temperatureMinFahrenheitValue.text =
                "${celsiusToFahrenheit(viewModel.weatherModel?.main?.temp_min)}°F"
            temperatureView.humidityValue.text = "${viewModel.weatherModel?.main?.humidity}%"
            temperatureView.root.visibility = View.VISIBLE
        }
    }

    private fun initListener() {
        binding.searchCityButton.setOnClickListener {
            val cityName = binding.cityNameEditText.text.toString()
            setSharedPreferences.putString(CITY_NAME, cityName)
            setSharedPreferences.apply()
            viewModel.refreshData(cityName)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun onObservable() {
        viewModel.weatherData.observe(this) { data ->
            binding.apply {
                data?.let {
                    cityCodeTextView.text = it.sys.country
                    cityNameTextView.text = it.name
                    dateTimeTextView.text = epochToDateTime(it.dt.toLong())
                    errorTextView.visibility = View.GONE
                    progressbarLoading.visibility = View.GONE
                    cityCodeTextView.visibility = View.VISIBLE
                    cityNameTextView.visibility = View.VISIBLE
                    dateTimeTextView.visibility = View.VISIBLE
                    temperatureView.weatherImage.setImageResource(it.weather.first().icon.image)
                    temperatureView.temperatureCelsiusValue.text = "${it.main.temp}°C"
                    temperatureView.temperatureFahrenheitValue.text =
                        "${celsiusToFahrenheit(it.main.temp)}°F"
                    temperatureView.temperatureFeelLikeCelsiusValue.text = "${it.main.feels_like}°C"
                    temperatureView.temperatureFeelLikeFahrenheitValue.text =
                        "${celsiusToFahrenheit(it.main.feels_like)}°F"
                    temperatureView.temperatureMaxCelsiusValue.text = "${it.main.temp_max}°C"
                    temperatureView.temperatureMaxFahrenheitValue.text =
                        "${celsiusToFahrenheit(it.main.temp_max)}°F"
                    temperatureView.temperatureMinCelsiusValue.text = "${it.main.temp_min}°C"
                    temperatureView.temperatureMinFahrenheitValue.text =
                        "${celsiusToFahrenheit(it.main.temp_min)}°F"
                    temperatureView.humidityValue.text = "${data.main.humidity}%"
                    temperatureView.root.visibility = View.VISIBLE
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
                        temperatureView.root.visibility = View.GONE
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
                        temperatureView.root.visibility = View.GONE
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

    private fun celsiusToFahrenheit(celsius: Double?): String {
        var fahrenheit = ""
        celsius?.let {
            fahrenheit = "%.2f".format(celsius * 1.8 + 32)
        }
        return fahrenheit
    }

    companion object {
        const val CITY_NAME = "CITY_NAME"
        const val DEFAULT_CITY = "BANGKOK"
        const val EXTRA_WEATHER_MODEL = "EXTRA_WEATHER_MODEL"
    }
}