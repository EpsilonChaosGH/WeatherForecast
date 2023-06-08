package com.example.data.entity

data class AirResponse(
    val list: List<ListElement>
) {
    data class ListElement(
        val components: Map<String, Double>,
    )
}