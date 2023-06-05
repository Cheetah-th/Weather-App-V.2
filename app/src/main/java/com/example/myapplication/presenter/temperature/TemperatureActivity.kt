package com.example.myapplication.presenter.temperature

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityTemperatureBinding
import com.example.myapplication.datasource.model.WeatherModel
import com.example.myapplication.utils.FormatString
import org.koin.android.ext.android.inject

class TemperatureActivity : AppCompatActivity() {

    private val viewModel: TemperatureViewModel by inject()
    private val formatString: FormatString by lazy { FormatString() }
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
        viewModel.weatherModel = intent.getParcelableExtra(EXTRA_WEATHER_MODEL) ?: WeatherModel()
    }

    private fun initView() {
        binding.apply {
            cityCodeTextView.text = viewModel.weatherModel.sys.country
            cityNameTextView.text = viewModel.weatherModel.name
            dateTimeTextView.text = formatString.epochToDateTime(viewModel.weatherModel.dt.toLong())

            cityCodeTextView.visibility = View.VISIBLE
            cityNameTextView.visibility = View.VISIBLE
            dateTimeTextView.visibility = View.VISIBLE

            temperatureView.weatherImage.setImageResource(viewModel.weatherModel.weather.first().icon.image)

            temperatureView.temperatureCelsiusValue.text =
                formatString.celsiusFormat(viewModel.weatherModel.main.temp)
            temperatureView.temperatureFahrenheitValue.text =
                formatString.celsiusToFahrenheitFormat(viewModel.weatherModel.main.temp)

            temperatureView.temperatureFeelLikeCelsiusValue.text =
                formatString.celsiusFormat(viewModel.weatherModel.main.feels_like)
            temperatureView.temperatureFeelLikeFahrenheitValue.text =
                formatString.celsiusToFahrenheitFormat(viewModel.weatherModel.main.feels_like)

            temperatureView.temperatureMaxCelsiusValue.text =
                formatString.celsiusFormat(viewModel.weatherModel.main.temp_max)
            temperatureView.temperatureMaxFahrenheitValue.text =
                formatString.celsiusToFahrenheitFormat(viewModel.weatherModel.main.temp_max)

            temperatureView.temperatureMinCelsiusValue.text =
                formatString.celsiusFormat(viewModel.weatherModel.main.temp_min)
            temperatureView.temperatureMinFahrenheitValue.text =
                formatString.celsiusToFahrenheitFormat(viewModel.weatherModel.main.temp_min)

            temperatureView.humidityValue.text =
                formatString.humidityFormat(viewModel.weatherModel.main.humidity)
            temperatureView.root.visibility = View.VISIBLE
        }
    }

    private fun initListener() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_WEATHER_MODEL = "EXTRA_WEATHER_MODEL"
    }
}