package com.example.myapplication.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sys(
    val country: String = "",
    val sunrise: Int = 0,
    val sunset: Int = 0
) : Parcelable