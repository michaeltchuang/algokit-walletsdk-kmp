package com.michaeltchuang.walletsdk.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
sealed class DataResource<T> {
    data class Success<T>(
        val data: T,
    ) : DataResource<T>()

    sealed class Error<T>(
        open val exception: Throwable? = null,
        open val code: Int? = null,
        open val data: T? = null,
    ) : DataResource<T>() {
        data class Api<T>(
            override val exception: Throwable,
            override val code: Int?,
        ) : Error<T>(exception, code)

        data class Local<T>(
            override val exception: Throwable,
        ) : Error<T>(exception)
    }

    class Loading<T> : DataResource<T>()

    suspend fun useSuspended(
        onSuccess: (suspend (T) -> Unit)? = null,
        onFailed: (suspend (Error<T>) -> Unit)? = null,
        onLoading: (suspend () -> Unit)? = null,
    ) {
        when (this) {
            is Success -> onSuccess?.invoke(data)
            is Error<T> -> onFailed?.invoke(this)
            is Loading -> onLoading?.invoke()
        }
    }
}
