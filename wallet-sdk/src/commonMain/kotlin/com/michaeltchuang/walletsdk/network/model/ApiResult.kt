package com.michaeltchuang.walletsdk.network.model

/**
 * A generic wrapper for API responses that can represent success or failure states
 */
sealed class ApiResult<out T> {

    /**
     * Represents a successful API response
     */
    data class Success<T>(val data: T) : ApiResult<T>()

    /**
     * Represents an API error response
     */
    data class Error(
        val code: Int,
        val message: String,
        val exception: Throwable? = null
    ) : ApiResult<Nothing>()

    /**
     * Represents a network error (no response from server)
     */
    data class NetworkError(val exception: Throwable) : ApiResult<Nothing>()

    /**
     * Check if this result is successful
     */
    val isSuccess: Boolean
        get() = this is Success

    /**
     * Check if this result is an error
     */
    val isError: Boolean
        get() = this is Error || this is NetworkError

    /**
     * Get the data if successful, null otherwise
     */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    /**
     * Get the error message if this is an error result
     */
    fun getErrorMessage(): String? = when (this) {
        is Error -> message
        is NetworkError -> exception.message ?: "Network error occurred"
        else -> null
    }
}