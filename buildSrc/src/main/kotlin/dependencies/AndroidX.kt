package com.example.internal.dependencies

import com.example.internal.Versions

object AndroidX {

    /**
     * [Core Kotlin Extensions](https://developer.android.com/kotlin/ktx#core)
     * Kotlin extensions for 'core' artifact.
     */
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"

    /**
     * [Fragment ktx](https://developer.android.com/jetpack/androidx/releases/fragment)
     */
    const val ktxFragment = "androidx.fragment:fragment-ktx:${Versions.ktxFragment}"

    /**
     * [Activity ktx](https://developer.android.com/jetpack/androidx/releases/activity)
     */
    const val ktxActivity = "androidx.activity:activity-ktx:${Versions.ktxActivity}"

    /**
     * [Appcompat](https://developer.android.com/jetpack/androidx/releases/activity)
     */
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

    /**
     * [Constraint layout](https://developer.android.com/jetpack/androidx/releases/constraintlayout)
     */
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    /**
     * [Jetpack navigation component](https://developer.android.com/guide/navigation/get-started#kts)
     */
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"

    /**
     * [Swipe refresh](https://developer.android.com/develop/ui/views/touch-and-input/swipe/add-swipe-interface)
     */
    const val swipeRefresh =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
}