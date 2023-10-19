package com.example.data.mappers

import com.example.data.entity.Coordinates
import com.example.data.source.network.response.GeocodingResponse

internal fun List<GeocodingResponse>.toCoordinates(): Coordinates {
    return Coordinates(
        lon = this.firstOrNull()?.lon.toString(),
        lat = this.firstOrNull()?.lat.toString(),
    )
}