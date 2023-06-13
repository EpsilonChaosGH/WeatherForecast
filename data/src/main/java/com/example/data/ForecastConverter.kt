package com.example.data

import androidx.room.TypeConverter
import com.example.domain.entity.ForecastEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


import javax.inject.Inject


class ForecastConverter {

    private val type = Types.newParameterizedType(List::class.java, ForecastEntity::class.java)
    private val adapter: JsonAdapter<List<ForecastEntity>> = Moshi.Builder().build().adapter(type)

    @TypeConverter
    fun listToJsonString(value: List<ForecastEntity>): String = adapter.toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = adapter.fromJson(value)
}