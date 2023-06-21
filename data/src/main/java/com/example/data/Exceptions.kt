package com.example.data

import android.database.sqlite.SQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext


open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class ConnectionException(
    cause: Throwable
) : AppException(cause = cause)

class BackendException(
    val code: Int,
    message: String
) : AppException(message)

class CityNotFoundException(
    cause: Throwable
) : AppException(cause = cause)

class InvalidApiKeyException(
    cause: Throwable
) : AppException(cause = cause)

class RequestRateLimitException(
    cause: Throwable
) : AppException(cause = cause)

class StorageException : AppException()

internal inline fun <T> wrapBackendExceptions(block: () -> T): T {
    try {
        return block.invoke()
    } catch (e: BackendException) {
        when (e.code) {
            401 -> throw InvalidApiKeyException(e)
            404 -> throw CityNotFoundException(e)
            429 -> throw RequestRateLimitException(e)
            else -> throw e
        }
    }
}

suspend fun <T> wrapSQLiteException(
    dispatcher: CoroutineDispatcher,
    block: suspend CoroutineScope.() -> T
): T {
    try {
        return withContext(dispatcher, block)
    } catch (e: SQLiteException) {
        val appException = StorageException()
        appException.initCause(e)
        throw appException
    }
}