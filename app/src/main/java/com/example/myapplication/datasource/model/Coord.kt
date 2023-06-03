package com.example.myapplication.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coord(
    val lat: Double = 0.0,
    val lon: Double = 0.0
) : Parcelable