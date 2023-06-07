@file:Suppress("unused")

package com.example.internal.dependencies

import com.example.internal.Versions

object Room {
    /**
     * [Android Room Runtime](https://mvnrepository.com/artifact/androidx.room/room-runtime)
     */
    const val runtime = "androidx.room:room-runtime:${Versions.room}"

    /**
     * [Android Room Kotlin Extensions](https://mvnrepository.com/artifact/androidx.room/room-ktx)
     */
    const val ktx = "androidx.room:room-ktx:${Versions.room}"

    /**
     * [Android Room Compiler](https://mvnrepository.com/artifact/androidx.room/room-compiler)
     */
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
}