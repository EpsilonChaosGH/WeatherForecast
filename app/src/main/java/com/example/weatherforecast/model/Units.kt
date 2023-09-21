package com.example.weatherforecast.model

import androidx.annotation.DrawableRes
import com.example.weatherforecast.R

enum class Units(val tempLabel: String, @DrawableRes val iconResId: Int, val value: String) {
    STANDARD("Kelvin", R.drawable.ic_kelvin, "standard"),
    METRIC("Celsius", R.drawable.ic_celsius, "metric"),
    IMPERIAL("Fahrenheit", R.drawable.ic_fahrenheit, "imperial");

    companion object {
        fun getUnitsSpinnerItem(): Array<SpinnerItem> {
            return Units.values().map {
                SpinnerItem(it.tempLabel, it.iconResId)
            }.toTypedArray()
        }
    }
}

//"°C °F"