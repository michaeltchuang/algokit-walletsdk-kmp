package com.michaeltchuang.walletsdk.network.service

import com.michaeltchuang.walletsdk.network.model.AccountInformationResponse
import com.michaeltchuang.walletsdk.network.model.ApiResult
import com.michaeltchuang.walletsdk.settings.domain.provideNodePreferenceRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.first

class AccountInformationApiServiceImpl(
    private val httpClient: HttpClient,
) : AccountInformationApiService {
    override suspend fun getAccountInformation(
        address: String,
        excludes: String,
        includeClosedAccounts: Boolean,
    ): ApiResult<AccountInformationResponse> =
        try {
            // Get the current network's base URL
            val baseUrl = provideNodePreferenceRepository().getSavedNodePreferenceFlow().first().baseUrl

            val response: HttpResponse =
                httpClient.get("$baseUrl/v2/accounts/$address") {
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
                        message = "Account not found: $address",
                    )
                }

                else -> {
                    val errorMessage =
                        try {
                            response.body<String>()
                        } catch (e: Exception) {
                            "HTTP ${response.status.value}: ${response.status.description}"
                        }

                    ApiResult.Error(
                        code = response.status.value,
                        message = errorMessage,
                    )
                }
            }
        } catch (e: Exception) {
            ApiResult.NetworkError(e)
        }
}
