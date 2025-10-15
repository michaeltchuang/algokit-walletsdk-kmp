package com.michaeltchuang.walletsdk.core.network.service

import com.michaeltchuang.walletsdk.core.network.model.AccountInformationResponse
import com.michaeltchuang.walletsdk.core.network.model.ApiResult

/**
 * API service interface for retrieving account information from Algorand node
 */
interface AccountInformationApiService {
    /**
     * Get account information for a given public key
     *
     * @param address The account's public key/address
     * @param excludes Comma-separated list of fields to exclude from response
     * @param includeClosedAccounts Whether to include closed accounts in the response
     * @return ApiResult containing AccountInformationResponse or error information
     */
    suspend fun getAccountInformation(
        address: String,
        excludes: String = "",
        includeClosedAccounts: Boolean = false,
    ): ApiResult<AccountInformationResponse>
}
