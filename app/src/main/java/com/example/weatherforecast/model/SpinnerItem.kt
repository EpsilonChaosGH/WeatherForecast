package com.example.weatherforecast.model

import androidx.annotation.DrawableRes

data class SpinnerItem(
    val title: String,
    @DrawableRes val iconResId: Int
)