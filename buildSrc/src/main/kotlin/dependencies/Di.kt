@file:Suppress("unused")

package com.example.internal.dependencies

import com.example.internal.Versions

object Di {
    /**
     * [Hilt Android](https://mvnrepository.com/artifact/com.google.dagger/hilt-android)
     * A fast dependency injector for Android and Java.
     */
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltCore}"

    /**
     * [Hilt Processor](https://mvnrepository.com/artifact/com.google.dagger/hilt-compiler)
     * A fast dependency injector for Android and Java.
     */
    const val daggerHiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltCore}"
}