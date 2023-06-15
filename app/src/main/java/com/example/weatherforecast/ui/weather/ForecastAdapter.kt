package com.example.weatherforecast.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.databinding.ItemForecastBinding
import com.example.weatherforecast.entity.ForecastState

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    class ViewHolder(
        val itemForecastBinding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(itemForecastBinding.root)

    var forecastList: List<ForecastState> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = forecastList[position]
        bind(holder, forecast)
    }

    override fun getItemCount() = forecastList.size

    private fun bind(holder: ViewHolder, forecast: ForecastState) {
        with(holder.itemForecastBinding) {
            timeTextView.text = forecast.data
            temperatureTextView.text = forecast.temperature

            imageView.setImageResource(forecast.weatherType.iconResId)
        }
    }
}

//return ViewHolder(
//ItemForecastBinding.bind(parent)
//)