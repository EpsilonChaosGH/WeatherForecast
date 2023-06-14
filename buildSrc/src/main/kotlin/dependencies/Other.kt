@file:Suppress("unused")

package com.example.internal.dependencies

import com.example.internal.Versions

object Other {

    /**
     * [Play Services](https://developers.google.com/android/guides/setup)
     */
    const val playServices =
        "com.google.android.gms:play-services-location:${Versions.playServices}"

    /**
     * [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
     */
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    /**
     * [Material Components For Android](https://mvnrepository.com/artifact/com.google.android.material/material)
     * Material Components for Android is a static library that you can add to your Android application in order to use APIs that provide
     * implementations of the Material Design specification. Compatible on devices running API 14 or later.
     */
    const val material = "com.google.android.material:material:${Versions.material}"

    /**
     * [View Binding Property Delegate](https://github.com/androidbroadcast/ViewBindingPropertyDelegate)
     * Make work with Android View Binding simpler.
     */
    const val viewBindingPropDel =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBindingPropDel}"
    const val viewBindingPropDelNoRef =
        "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Versions.viewBindingPropDelNoRef}"

}