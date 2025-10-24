package com.michaeltchuang.walletsdk.core.account.domain.usecase.core

import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetBasicAccountInformationUseCase
import com.michaeltchuang.walletsdk.core.network.model.AccountInformation
import com.michaeltchuang.walletsdk.core.network.model.ApiResult
import com.michaeltchuang.walletsdk.core.network.service.AccountInformationApiService
import com.michaeltchuang.walletsdk.core.network.service.getBasicAccountInformation

internal class GetBasicAccountInformationUseCaseImpl(
    private val accountInformationApiService: AccountInformationApiService,
) : GetBasicAccountInformationUseCase {
    override suspend fun invoke(address: String): AccountInformation? =
        when (
            val result =
                accountInformationApiService.getBasicAccountInformation(address)
        ) {
            is ApiResult.Success -> {
                result.data.accountInformation
            }

            is ApiResult.Error -> null
            is ApiResult.NetworkError -> null
        }
}
