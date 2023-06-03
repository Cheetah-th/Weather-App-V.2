package com.example.myapplication.datasource.model

import com.example.myapplication.R
import com.google.gson.annotations.SerializedName

enum class WeatherType(val image: Int) {
    @SerializedName("01d")
    CLEAR_SKY_DAY(R.drawable.ic_01d),
    @SerializedName("01n")
    CLEAR_SKY_NIGHT(R.drawable.ic_01n),
    @SerializedName("02d")
    FEW_CLOUDS_DAY(R.drawable.ic_02d),
    @SerializedName("02n")
    FEW_CLOUDS_NIGHT(R.drawable.ic_02n),
    @SerializedName("03d")
    SCATTERED_CLOUDS_DAY(R.drawable.ic_03d),
    @SerializedName("03n")
    SCATTERED_CLOUDS_NIGHT(R.drawable.ic_03n),
    @SerializedName("04d")
    BROKEN_CLOUDS_DAY(R.drawable.ic_04d),
    @SerializedName("04n")
    BROKEN_CLOUDS_NIGHT(R.drawable.ic_04n),
    @SerializedName("09d")
    SHOWER_RAIN_DAY(R.drawable.ic_09d),
    @SerializedName("09n")
    SHOWER_RAIN_NIGHT(R.drawable.ic_09n),
    @SerializedName("10d")
    RAIN_DAY(R.drawable.ic_10d),
    @SerializedName("10n")
    RAIN_NIGHT(R.drawable.ic_10n),
    @SerializedName("11d")
    THUNDERSTORM_DAY(R.drawable.ic_11d),
    @SerializedName("11n")
    THUNDERSTORM_NIGHT(R.drawable.ic_11n),
    @SerializedName("13d")
    SNOW_DAY(R.drawable.ic_13d),
    @SerializedName("13n")
    SNOW_NIGHT(R.drawable.ic_13n),
    @SerializedName("50d")
    MIST_DAY(R.drawable.ic_50d),
    @SerializedName("50n")
    MIST_NIGHT(R.drawable.ic_50n),
    OTHER(R.drawable.ic_holder),
}