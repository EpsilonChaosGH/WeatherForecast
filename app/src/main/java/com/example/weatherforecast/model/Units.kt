package com.example.weatherforecast.model

import androidx.annotation.DrawableRes
import com.example.weatherforecast.R

enum class Units(
    val tempLabel: String,
    @DrawableRes val iconResId: Int,
    val unitsValue: String,
    val units: String
) {
    STANDARD("Kelvin", R.drawable.ic_kelvin, "standard", "K"),
    METRIC("Celsius", R.drawable.ic_celsius, "metric", "°C"),
    IMPERIAL("Fahrenheit", R.drawable.ic_fahrenheit, "imperial", "°F");

    companion object {
        fun getUnitsSpinnerItem(): Array<SpinnerItem> {
            return Units.values().map {
                SpinnerItem(it.tempLabel, it.iconResId)
            }.toTypedArray()
        }

        fun getUnitsValue(index: Int): String {
            return Units.values()[index].unitsValue
        }

        fun getIndex(value: String): Int {
            var result = 0
            Units.values().forEachIndexed { index, units ->
                if (units.unitsValue == value) result = index
            }
            return result
        }

        fun getUnits(value: String): String {
            val index = getIndex(value)
            return Units.values()[index].units
        }
    }
}