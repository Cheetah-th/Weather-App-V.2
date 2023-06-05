package com.example.myapplication.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormatString {

    fun epochToDateTime(epoch: Long): String {
        val date = Date(epoch.times(1000))
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

    fun celsiusFormat(celsius: Double): String {
        return "${"%.2f".format(celsius)}°C"
    }

    fun celsiusToFahrenheitFormat(celsius: Double): String {
        return "${"%.2f".format(celsius * 1.8 + 32)}°F"
    }

    fun humidityFormat(humidity: Int): String {
        return "${humidity}%"
    }
}