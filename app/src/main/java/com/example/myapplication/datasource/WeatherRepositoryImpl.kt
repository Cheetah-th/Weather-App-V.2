package com.example.myapplication.datasource

import com.example.myapplication.datasource.model.WeatherModel
import com.example.myapplication.datasource.service.WeatherAPIService
import io.reactivex.Single

class WeatherRepositoryImpl(private val weatherApi: WeatherAPIService) : WeatherRepository {
    override fun getWeatherData(cityName: String): Single<WeatherModel> {
        return weatherApi.getDataService(cityName)
    }
}
