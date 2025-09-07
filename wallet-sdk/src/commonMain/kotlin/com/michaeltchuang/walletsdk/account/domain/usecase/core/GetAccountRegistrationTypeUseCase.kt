package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.model.core.AccountRegistrationType
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetAccountRegistrationType
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetLocalAccounts

class GetAccountRegistrationTypeUseCase(
    private val getLocalAccounts: GetLocalAccounts
) : GetAccountRegistrationType {

    override suspend fun invoke(address: String): AccountRegistrationType? {
        return when (getLocalAccounts().firstOrNull { it.algoAddress == address }) {
            is LocalAccount.Algo25 -> AccountRegistrationType.Algo25
            is LocalAccount.LedgerBle -> AccountRegistrationType.LedgerBle
            is LocalAccount.NoAuth -> AccountRegistrationType.NoAuth
            is LocalAccount.HdKey -> AccountRegistrationType.HdKey
            else -> null
        }
    }

    override fun invoke(account: LocalAccount): AccountRegistrationType {
        return when (account) {
            is LocalAccount.Algo25 -> AccountRegistrationType.Algo25
            is LocalAccount.LedgerBle -> AccountRegistrationType.LedgerBle
            is LocalAccount.NoAuth -> AccountRegistrationType.NoAuth
            is LocalAccount.HdKey -> AccountRegistrationType.HdKey
        }
    }
}
