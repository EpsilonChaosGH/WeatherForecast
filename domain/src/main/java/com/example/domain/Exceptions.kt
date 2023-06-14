package com.example.domain


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