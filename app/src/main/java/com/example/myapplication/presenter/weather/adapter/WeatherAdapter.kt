package com.example.myapplication.presenter.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemWeatherBinding
import com.example.myapplication.datasource.model.Weather

class WeatherAdapter(private val weatherItems: List<Weather>) :
    RecyclerView.Adapter<WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherItem = weatherItems[position]
        holder.bind(weatherItem)
    }

    override fun getItemCount(): Int {
        return weatherItems.size
    }
}