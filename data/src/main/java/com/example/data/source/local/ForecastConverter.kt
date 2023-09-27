package com.example.data.source.local

import androidx.room.TypeConverter
import com.example.data.entity.ForecastEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


class ForecastConverter {

    private val type = Types.newParameterizedType(List::class.java, ForecastEntity::class.java)
    private val adapter: JsonAdapter<List<ForecastEntity>> = Moshi.Builder().build().adapter(type)

    @TypeConverter
    fun listToJsonString(value: List<ForecastEntity>): String = adapter.toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = adapter.fromJson(value)
}