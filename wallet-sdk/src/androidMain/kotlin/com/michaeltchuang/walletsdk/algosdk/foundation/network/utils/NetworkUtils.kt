 

package com.michaeltchuang.walletsdk.algosdk.foundation.network.utils

/*
import com.michaeltchuang.walletsdk.algosdk.foundation.AlgoKitResult
import com.michaeltchuang.walletsdk.algosdk.foundation.network.exceptions.PeraRetrofitErrorHandler
import java.io.IOException
import retrofit2.Response
import kotlin.invoke

*/
/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [Result.Error] is
 * created based on the [errorMessage].
 *//*

suspend fun <T : Any> safeApiCall(call: suspend () -> AlgoKitResult<T>): AlgoKitResult<T> {
    return try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        AlgoKitResult.Error(IOException(null, e))
    }
}

suspend fun <T : Any> request(
    onFailed: ((Response<T>) -> AlgoKitResult<T>)? = null,
    doRequest: suspend () -> Response<T>
): AlgoKitResult<T> {
    return safeApiCall {
        with(doRequest()) {
            if (isSuccessful && body() != null) {
                AlgoKitResult.Success(body() as T)
            } else {
                onFailed?.invoke(this) ?: AlgoKitResult.Error(Exception(errorBody().toString()), code())
            }
        }
    }
}

suspend fun <T : Any> requestWithHipoErrorHandler(
    peraApiErrorHandler: PeraRetrofitErrorHandler,
    doRequest: suspend () -> Response<T>
): AlgoKitResult<T> {
    return request(
        doRequest = doRequest,
        onFailed = { errorResponse -> peraApiErrorHandler.getMessageAsResultError(errorResponse) }
    )
}

fun <T : Any> PeraRetrofitErrorHandler.getMessageAsResultError(response: Response<T>): AlgoKitResult<T> {
    return AlgoKitResult.Error(Exception(parse(response).message))
}
*/
