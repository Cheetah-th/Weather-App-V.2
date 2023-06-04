package com.example.myapplication.presenter.temperature

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemperatureBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initData()
        initView()
        initListener()
    }

    private fun initData() {
        viewModel.weatherModel = intent.getParcelableExtra(EXTRA_WEATHER_MODEL)
    }

    private fun initView() {
        binding.apply {
            cityCodeTextView.text = viewModel.weatherModel?.sys?.country
            cityNameTextView.text = viewModel.weatherModel?.name
            dateTimeTextView.text = epochToDateTime(viewModel.weatherModel?.dt?.toLong())
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
        binding.backButton.setOnClickListener {
            finish()
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
        const val EXTRA_WEATHER_MODEL = "EXTRA_WEATHER_MODEL"
    }
}