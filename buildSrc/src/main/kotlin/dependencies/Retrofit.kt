@file:Suppress("unused")

package com.example.internal.dependencies

import com.example.internal.Versions

object Retrofit {
    /**
     * [Converter: Gson](https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-moshi)
     * A Retrofit Converter which uses Moshi for serialization.
     */
    const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit2}"

    /**
     * [Retrofit](https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit)
     * A type-safe HTTP client for Android and Java.
     */
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"


    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
}