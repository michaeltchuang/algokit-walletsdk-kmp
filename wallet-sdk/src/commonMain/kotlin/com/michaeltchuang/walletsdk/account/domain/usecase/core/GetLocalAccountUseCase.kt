package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetLocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetLocalAccounts


internal class GetLocalAccountUseCase(
    private val getLocalAccounts: GetLocalAccounts
) : GetLocalAccount {
    override suspend fun invoke(address: String): LocalAccount? {
        return getLocalAccounts().firstOrNull { it.algoAddress == address }
    }
}
