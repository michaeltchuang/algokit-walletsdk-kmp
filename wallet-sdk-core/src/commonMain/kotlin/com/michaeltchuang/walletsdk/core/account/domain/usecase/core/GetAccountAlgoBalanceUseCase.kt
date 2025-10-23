package com.michaeltchuang.walletsdk.core.account.domain.usecase.core

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetAccountAlgoBalance
import com.michaeltchuang.walletsdk.core.network.model.ApiResult
import com.michaeltchuang.walletsdk.core.network.service.AccountInformationApiService
import com.michaeltchuang.walletsdk.core.network.service.getBasicAccountInformation

internal class GetAccountAlgoBalanceUseCase(
    private val accountInformationApiService: AccountInformationApiService,
) : GetAccountAlgoBalance {
    override suspend fun invoke(address: String): BigInteger? {
        return when (val result =
            accountInformationApiService.getBasicAccountInformation(address)) {
            is ApiResult.Success -> {
                result.data.accountInformation?.amount?.let { amountString ->
                    try {
                        amountString.toBigInteger()
                    } catch (e: Exception) {
                        null
                    }
                }
            }

            is ApiResult.Error -> null
            is ApiResult.NetworkError -> null
        }
    }
}
