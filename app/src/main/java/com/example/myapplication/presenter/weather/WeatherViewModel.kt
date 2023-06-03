package com.example.myapplication.presenter.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.datasource.model.WeatherModel
import com.example.myapplication.domain.GetWeatherDataUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(private val getWeatherDataUseCase: GetWeatherDataUseCase) : ViewModel() {

    private val disposable = CompositeDisposable()

    var weatherModel: WeatherModel? = WeatherModel()

    private val _weatherData: MutableLiveData<WeatherModel> by lazy { MutableLiveData() }
    val weatherData: LiveData<WeatherModel> by lazy { _weatherData }

    private val _weatherError: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val weatherError: LiveData<Boolean> by lazy { _weatherError }

    private val _weatherLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val weatherLoading: LiveData<Boolean> by lazy { _weatherLoading }

    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }

    private fun getDataFromAPI(cityName: String) {
        _weatherLoading.value = true
        disposable.add(
            getWeatherDataUseCase.execute(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object : DisposableSingleObserver<WeatherModel>() {
                        override fun onSuccess(result: WeatherModel) {
                            weatherModel = result
                            _weatherData.value = result
                            _weatherError.value = false
                            _weatherLoading.value = false
                        }

                        override fun onError(e: Throwable) {
                            _weatherError.value = true
                            _weatherLoading.value = false
                        }
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
