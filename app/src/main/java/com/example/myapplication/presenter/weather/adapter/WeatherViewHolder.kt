package com.example.myapplication.presenter.weather.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemWeatherBinding
import com.example.myapplication.datasource.model.Weather

class WeatherViewHolder(private val binding: ItemWeatherBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(weatherItem: Weather) {
        binding.weatherImage.setImageResource(weatherItem.icon.image)
        binding.weatherMainValue.text = weatherItem.main
        binding.weatherDescriptionValue.text = weatherItem.description
    }
}