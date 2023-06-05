package com.example.myapplication.presenter.temperature

import androidx.lifecycle.ViewModel
import com.example.myapplication.datasource.model.WeatherModel
import io.reactivex.disposables.CompositeDisposable

class TemperatureViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    var weatherModel: WeatherModel = WeatherModel()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}