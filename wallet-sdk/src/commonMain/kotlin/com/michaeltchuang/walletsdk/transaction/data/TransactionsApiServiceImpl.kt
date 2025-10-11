package com.michaeltchuang.walletsdk.transaction.data

import com.michaeltchuang.walletsdk.network.model.SendTransactionResponse
import com.michaeltchuang.walletsdk.network.model.TrackTransactionRequest
import com.michaeltchuang.walletsdk.network.model.TransactionParams
import com.michaeltchuang.walletsdk.network.service.TransactionApiService
import com.michaeltchuang.walletsdk.network.utils.getNodeBaseUrl
import com.michaeltchuang.walletsdk.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class TransactionsApiServiceImpl(
    private val httpClient: HttpClient,
) : TransactionApiService {
    override suspend fun getTransactionParams(): Result<TransactionParams> =
        try {
            val response: HttpResponse = httpClient.get("${getNodeBaseUrl()}/v2/transactions/params")
            when {
                response.status.isSuccess() -> {
                    val transactionParams = response.body<TransactionParams>()
                    Result.Success(transactionParams)
                }

                response.status == HttpStatusCode.Companion.NotFound -> {
                    Result.Error(
                        code = response.status.value,
                        exception = Exception("Transaction params not found"),
                    )
                }

                else -> {
                    Result.Error(
                        code = response.status.value,
                        exception = Exception(response.status.description),
                    )
                }
            }
        } catch (e: Exception) {
            Result.Error(
                code = -1,
                exception = e,
            )
        }

    // Added function: trackTransaction
    override suspend fun postTrackTransaction(trackTransactionRequest: TrackTransactionRequest): Result<Unit> =
        try {
            val response: HttpResponse =
                httpClient.post("${getNodeBaseUrl()}/v1/transactions/") {
                    contentType(ContentType.Application.Json)
                    setBody(trackTransactionRequest)
                }
            when {
                response.status.isSuccess() -> Result.Success(Unit)
                else -> {
                    Result.Error(
                        code = response.status.value,
                        exception = Exception("Transaction params not found"),
                    )
                }
            }
        } catch (e: Exception) {
            Result.Error(
                code = -1,
                exception = e,
            )
        }

    override suspend fun sendSignedTransaction(rawTransactionData: ByteArray): Result<SendTransactionResponse> =
        try {
            val response: HttpResponse =
                httpClient.post("${getNodeBaseUrl()}/v2/transactions") {
                    contentType(ContentType("application", "x-binary"))
                    setBody(rawTransactionData)
                }

            when {
                response.status.isSuccess() -> {
                    val result = response.body<SendTransactionResponse>()
                    Result.Success(result)
                }

                else -> {
                    val errorMessage =
                        try {
                            response.body<String>()
                        } catch (e: Exception) {
                            "HTTP ${response.status.value}: ${response.status.description}"
                        }
                    Result.Error(code = -1, exception = Exception(errorMessage))
                }
            }
        } catch (e: Exception) {
            Result.Error(code = -1, exception = e)
        }
}
