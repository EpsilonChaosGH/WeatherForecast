package com.example.weatherforecast.model

import androidx.annotation.DrawableRes
import com.example.weatherforecast.R

enum class Units(val tempLabel: String, @DrawableRes val iconResId: Int, val value: String) {
    STANDARD("°F", R.drawable.ic_baseline_favorite_border_24, "standard"),
    METRIC("°C", R.drawable.ic_baseline_favorite_border_24, "metric"),
    IMPERIAL("°F", R.drawable.ic_baseline_favorite_border_24, "imperial");

    companion object {
        fun getUnitsSpinnerItem(): Array<SpinnerItem> {
            return Units.values().map {
                SpinnerItem(it.tempLabel, it.iconResId)
            }.toTypedArray()
        }
    }
}
