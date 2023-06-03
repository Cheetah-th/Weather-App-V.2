package com.example.myapplication.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val description: String,
    val icon: WeatherType = WeatherType.OTHER,
    val id: Int = 0,
    val main: String = ""
) : Parcelable