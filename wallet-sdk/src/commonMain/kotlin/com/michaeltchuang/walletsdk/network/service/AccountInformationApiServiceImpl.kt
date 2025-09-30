package com.michaeltchuang.walletsdk.network.service

import com.michaeltchuang.walletsdk.network.model.AccountInformationResponse
import com.michaeltchuang.walletsdk.network.model.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess

class AccountInformationApiServiceImpl(
    private val httpClient: HttpClient,
    private val baseUrlProvider: suspend () -> String = { "https://testnet-idx.algonode.cloud" }
) : AccountInformationApiService {

    override suspend fun getAccountInformation(
        publicKey: String,
        excludes: String,
        includeClosedAccounts: Boolean
    ): ApiResult<AccountInformationResponse> {
        return try {
            // Get the current network's base URL
            val baseUrl = baseUrlProvider()

            val response: HttpResponse = httpClient.get("$baseUrl/v2/accounts/$publicKey") {
                if (excludes.isNotEmpty()) {
                    parameter("exclude", excludes)
                }
                parameter("include-all", includeClosedAccounts)
            }

            when {
                response.status.isSuccess() -> {
                    val accountInfo = response.body<AccountInformationResponse>()
                    ApiResult.Success(accountInfo)
                }

                response.status == HttpStatusCode.NotFound -> {
                    ApiResult.Error(
                        code = response.status.value,
                        message = "Account not found: $publicKey"
                    )
                }

                else -> {
                    val errorMessage = try {
                        response.body<String>()
                    } catch (e: Exception) {
                        "HTTP ${response.status.value}: ${response.status.description}"
                    }

                    ApiResult.Error(
                        code = response.status.value,
                        message = errorMessage
                    )
                }
            }
        } catch (e: Exception) {
            ApiResult.NetworkError(e)
        }
    }
}