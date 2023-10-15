package com.example.weatherforecast.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entity.City
import com.example.weatherforecast.databinding.ItemFavoriteBinding
import com.example.weatherforecast.model.FavoritesItem

interface FavoritesListener {
    fun showDetails(city: City)
}

class FavoritesDiffCallback(
    private val oldList: List<FavoritesItem>,
    private val newList: List<FavoritesItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = oldList[oldItemPosition]
        val newList = newList[newItemPosition]
        return oldList.city == newList.city
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = oldList[oldItemPosition]
        val newList = newList[newItemPosition]
        return oldList == newList
    }
}

class FavoritesAdapter(
    private val favoritesListener: FavoritesListener
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    class ViewHolder(
        private val binding: ItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: FavoritesItem, listener: FavoritesListener) = with(binding) {
            cityNameTextView.text = item.city
            temperatureTextView.text = item.temperature
            currentDateTextView.text = item.data
            currentWeatherTextView.text = item.description
            weatherIconImageView.setImageResource(item.weatherType.iconResId)

            itemView.setOnClickListener { listener.showDetails(City(item.city)) }
        }
    }

    var items = listOf<FavoritesItem>()
        set(newValue) {
            val diffCallback = FavoritesDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], favoritesListener)
    }

    override fun getItemCount(): Int = items.size
}