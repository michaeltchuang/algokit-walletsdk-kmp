package com.michaeltchuang.walletsdk.core.foundation.utils

import kotlinx.io.IOException

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(
        val data: T,
    ) : Result<T>()

    data class Error(
        val exception: Exception,
        val code: Int? = null,
    ) : Result<Nothing>() {
        fun getAsResourceError(): String =
            when (exception) {
                is IOException -> {
                    exception.message.toString()
                }
                else -> exception.message.toString()
            }
    }

    suspend fun use(
        onSuccess: (suspend (T) -> Unit)? = null,
        onFailed: (suspend (Exception, Int?) -> Unit)? = null,
    ) {
        when (this) {
            is Success -> onSuccess?.invoke(data)
            is Error -> onFailed?.invoke(exception, code)
        }
    }

    suspend fun <R : Any> map(transform: suspend (T) -> R): Result<R> =
        when (this) {
            is Success -> Success(transform(data))
            is Error -> Error(exception, code)
        }

    override fun toString(): String =
        when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
}
