package com.example.weatherforecast.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weatherforecast.R

enum class AirQuality(@DrawableRes val colorResId: Int, @StringRes val qualityResId: Int) {
    GOOD(R.color.green, R.string.good),
    FAIR(R.color.green, R.string.fair),
    MODERATE(R.color.yellow, R.string.moderate),
    POOR(R.color.orange, R.string.poor),
    VERY_POOR(R.color.red, R.string.very_poor),
    ERROR(R.color.red, R.string.error);
}