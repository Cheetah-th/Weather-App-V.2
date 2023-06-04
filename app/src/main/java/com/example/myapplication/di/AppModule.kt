package com.example.myapplication.di

import com.example.myapplication.datasource.WeatherRepository
import com.example.myapplication.datasource.WeatherRepositoryImpl
import com.example.myapplication.datasource.service.WeatherAPIService
import com.example.myapplication.domain.GetWeatherDataUseCase
import com.example.myapplication.presenter.temperature.TemperatureViewModel
import com.example.myapplication.presenter.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    GlobalContext.loadKoinModules(
        listOf(
            repositoryModule,
            networkModule,
            useCaseModule,
            viewModelModule
        )
    )
}

val networkModule = module {
    single { WeatherAPIService() }
}

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { GetWeatherDataUseCase(get()) }
}

val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
    viewModel { TemperatureViewModel() }
}