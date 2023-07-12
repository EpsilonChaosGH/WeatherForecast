package com.example.weatherforecast

import kotlin.random.Random


fun main() {
    val maxRange = 1000000
    var max = 0
    val list = (1..maxRange).toMutableList()

    for (i in 1..100) {
        val x = binarySearch(list, Random.nextInt(maxRange))
        println(x)
        if (x > max) max = x
    }
    println(max)
}

fun binarySearch(list: List<Int>, item: Int): Int {
    var start = 0
    var end = list.size - 1
    var middle: Int
    var counter = 0

    while (start <= end) {
        counter++
        middle = (start + end) / 2
        if (list[middle] == item) return counter
        if (list[middle] > item) end = middle - 1
        else start = middle + 1
    }
    return -1
}

