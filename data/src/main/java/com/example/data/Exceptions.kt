package com.example.data

import android.database.sqlite.SQLiteException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class EmptyFieldException(
    //val field: Field
) : AppException()

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

class ParseBackendResponseException(
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

suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: AppException) {
        throw e
    } catch (e: JsonDataException) {
        throw ParseBackendResponseException(e)
    } catch (e: JsonEncodingException) {
        throw ParseBackendResponseException(e)
    } catch (e: IOException) {
        throw ConnectionException(e)
    }
}