package com.example.weatherforecast.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.databinding.ItemForecastBinding
import com.example.weatherforecast.entity.ForecastState

class ForecastDiffCallback(
    private val oldList: List<ForecastState>,
    private val newList: List<ForecastState>
):DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldForecast = oldList[oldItemPosition]
        val newForecast = newList[newItemPosition]
        return oldForecast.data == newForecast.data
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldForecast = oldList[oldItemPosition]
        val newForecast = newList[newItemPosition]
        return oldForecast == newForecast
    }
}

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    class ViewHolder(
        val itemForecastBinding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(itemForecastBinding.root)

    var items: List<ForecastState> = emptyList()
        set(newValue) {
            val diffCallback = ForecastDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = items[position]
        bind(holder, forecast)
    }

    override fun getItemCount() = items.size

    private fun bind(holder: ViewHolder, forecast: ForecastState) {
        with(holder.itemForecastBinding) {
            timeTextView.text = forecast.data
            temperatureTextView.text = forecast.temperature

            imageView.setImageResource(forecast.weatherType.iconResId)
        }
    }
}