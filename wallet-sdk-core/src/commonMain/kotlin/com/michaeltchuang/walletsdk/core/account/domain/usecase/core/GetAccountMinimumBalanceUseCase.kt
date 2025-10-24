package com.michaeltchuang.walletsdk.core.account.domain.usecase.core

import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetAccountMinimumBalance
import com.michaeltchuang.walletsdk.core.network.model.ApiResult
import com.michaeltchuang.walletsdk.core.network.service.AccountInformationApiService
import com.michaeltchuang.walletsdk.core.network.service.getBasicAccountInformation

internal class GetAccountMinimumBalanceUseCase(
    private val accountInformationApiService: AccountInformationApiService,
) : GetAccountMinimumBalance {
    override suspend fun invoke(address: String): Long? =
        when (
            val result =
                accountInformationApiService.getBasicAccountInformation(address)
        ) {
            is ApiResult.Success -> {
                result.data.accountInformation?.minBalance
            }

            is ApiResult.Error -> null
            is ApiResult.NetworkError -> null
        }
}
