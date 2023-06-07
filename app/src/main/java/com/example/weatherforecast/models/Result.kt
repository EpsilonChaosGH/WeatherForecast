package com.example.weatherforecast.models

import com.example.domain.ResponseResult

val ResponseResult<*>?.isSucceeded get() = this != null && this is ResponseResult.Success && data != null

val ResponseResult<*>?.isError get() = this != null && this is ResponseResult.Error

inline infix fun <T, Value : Any> ResponseResult<T>?.runSucceeded(predicate: (data: T) -> Value): Value? {
    if (this != null && this.isSucceeded && this is ResponseResult.Success && this.data != null) {
        return predicate.invoke(this.data)
    }
    return null
}

inline infix fun <T> ResponseResult<T>.success(predicate: (data: T) -> Unit): ResponseResult<T> {
    if (this is ResponseResult.Success && this.data != null) {
        predicate.invoke(this.data)
    }
    return this
}

inline infix fun <T> ResponseResult<T>.error(predicate: (data: Exception) -> Unit) {
    if (this is ResponseResult.Error) {
        predicate.invoke(this.exception)
    }
}