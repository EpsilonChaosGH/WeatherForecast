package com.example.weatherforecast.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weatherforecast.R

enum class WeatherType(@DrawableRes val iconResId: Int, @StringRes val descResId: Int) {
    IC_01D(R.drawable.ic_sun, R.string.clear_sky),
    IC_02D(R.drawable.ic_cloudy, R.string.few_clouds),
    IC_03D(R.drawable.ic_cloud, R.string.scattered_clouds),
    IC_04D(R.drawable.ic_cloud, R.string.broken_clouds),
    IC_09D(R.drawable.ic_rain, R.string.shower_rain),
    IC_10D(R.drawable.ic_rain, R.string.rain),
    IC_11D(R.drawable.ic_thunderstorm, R.string.thunderstorm),
    IC_13D(R.drawable.ic_winter, R.string.snow),
    IC_50D(R.drawable.ic_fog, R.string.mist),
    IC_01N(R.drawable.ic_night, R.string.clear_sky_night),
    IC_02N(R.drawable.ic_night, R.string.few_clouds_night),
    IC_03N(R.drawable.ic_night, R.string.scattered_clouds_night),
    IC_04N(R.drawable.ic_night, R.string.broken_clouds_night),
    IC_09N(R.drawable.ic_rain, R.string.shower_rain_night),
    IC_10N(R.drawable.ic_rain, R.string.rain_night),
    IC_11N(R.drawable.ic_thunderstorm, R.string.thunderstorm_night),
    IC_13N(R.drawable.ic_winter, R.string.snow_night),
    IC_50N(R.drawable.ic_fog, R.string.mist_night),
    IC_UNKNOWN(R.drawable.ic_cloudy, R.string.unknown);

    companion object {
        fun find(value: String?): WeatherType {
            for (weather in values()) {
                if (weather.toString().equals(value, true))
                    return weather
            }
            return IC_UNKNOWN
        }
    }
}