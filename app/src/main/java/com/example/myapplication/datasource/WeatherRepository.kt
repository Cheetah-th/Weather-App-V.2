package com.example.myapplication.datasource

import com.example.myapplication.datasource.model.WeatherModel
import io.reactivex.Single

interface WeatherRepository {
    fun getWeatherData(cityName: String): Single<WeatherModel>
}