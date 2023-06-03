package com.example.myapplication.domain

import com.example.myapplication.datasource.WeatherRepository
import com.example.myapplication.datasource.model.WeatherModel
import io.reactivex.Single

class GetWeatherDataUseCase(private val weatherRepository: WeatherRepository) {
    fun execute(cityName: String): Single<WeatherModel> {
        return weatherRepository.getWeatherData(cityName)
    }
}