package com.example.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.*

const val FORMAT_EEE_d_MMMM_HH_mm = "EEE, d MMMM HH:mm"
const val FORMAT_EEE_HH_mm = "EEE, HH:mm"
const val FORMAT_HH_mm = "HH:mm"
const val FORMAT_dd_MM_yy_HH_mm = "dd-MM-yy HH:mm"

fun String.toTime(pattern: String) =
    SimpleDateFormat(pattern, Locale.getDefault()).parse(this)?.time ?: 0L

fun Long.format(pattern: String, timeZone: Long): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format((this + timeZone) * 1000)

fun Long.diffFormat(): String {
    return when (val diffTime = Calendar.getInstance().time.time - this) {
        in 0..60000 -> "Last update: just now"

        in 60000..120000 -> "Last update: " +
                SimpleDateFormat("m", Locale.getDefault()).format(diffTime) +
                " minute ago"

        in 120000..3600000 -> "Last update: " +
                SimpleDateFormat("m", Locale.getDefault()).format(diffTime) +
                " minutes ago"

        in 3600000..7200000 -> "Last update: " +
                SimpleDateFormat("k", Locale.getDefault()).format(diffTime) +
                " hour ago"

        in 7200000..86400000 -> "Last update: " +
                SimpleDateFormat("k", Locale.getDefault()).format(diffTime) +
                " hours ago"

        in 86400000..604800000 -> "Last update: " +
                SimpleDateFormat("d", Locale.getDefault()).format(diffTime) +
                " days ago"

        in 604800000..Long.MAX_VALUE -> "Last update: over a month ago"

        else -> ""
    }
}
