package com.michaeltchuang.walletsdk.network.service

import com.michaeltchuang.walletsdk.network.model.AccountInformation
import com.michaeltchuang.walletsdk.network.model.AccountInformationResponse
import com.michaeltchuang.walletsdk.network.model.ApiResult

/**
 * Extension functions for AccountInformationApiService to provide convenient methods
 * for common query patterns
 */

/**
 * Get basic account information excluding heavy data like apps and assets
 */
suspend fun AccountInformationApiService.getBasicAccountInformation(publicKey: String): ApiResult<AccountInformationResponse> =
    getAccountInformation(
        publicKey = publicKey,
        excludes = "apps-local-state,created-apps,assets,created-assets",
    )

/**
 * Get account information with only balance and status
 */
suspend fun AccountInformationApiService.getAccountBalance(publicKey: String): ApiResult<AccountInformationResponse> =
    getAccountInformation(
        publicKey = publicKey,
        excludes = "apps-local-state,created-apps,assets,created-assets,participation",
    )

/**
 * Get complete account information including all fields and closed accounts
 */
suspend fun AccountInformationApiService.getCompleteAccountInformation(publicKey: String): ApiResult<AccountInformationResponse> =
    getAccountInformation(
        publicKey = publicKey,
        excludes = "",
        includeClosedAccounts = true,
    )

/**
 * Check if an account exists (lightweight check)
 */
suspend fun AccountInformationApiService.accountExists(publicKey: String): Boolean =
    when (getBasicAccountInformation(publicKey)) {
        is ApiResult.Success -> true
        is ApiResult.Error -> false
        is ApiResult.NetworkError -> false
    }

/**
 * Get just the account information (unwrapped from the response)
 */
suspend fun AccountInformationApiService.getAccountInformationOnly(
    publicKey: String,
    excludes: String = "",
    includeClosedAccounts: Boolean = false,
): ApiResult<AccountInformation> =
    when (val result = getAccountInformation(publicKey, excludes, includeClosedAccounts)) {
        is ApiResult.Success -> {
            result.data.accountInformation?.let { accountInfo ->
                ApiResult.Success(accountInfo)
            } ?: ApiResult.Error(
                code = 404,
                message = "Account information not found in response",
            )
        }

        is ApiResult.Error -> result
        is ApiResult.NetworkError -> result
    }

/**
 * Get account balance amount directly
 */
suspend fun AccountInformationApiService.getAccountBalanceAmount(publicKey: String): String? =
    when (val result = getAccountBalance(publicKey)) {
        is ApiResult.Success -> result.data.accountInformation?.amount
        else -> null
    }
