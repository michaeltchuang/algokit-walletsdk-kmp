package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.account.domain.model.custom.AccountLite
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetAccountsCustomInfo

class NameRegistrationUseCase(
    private val accountAdditionUseCase: AccountAdditionUseCase,
    /*   private val getLocalAccountsUseCase: GetAlgo25AccountUseCase,
       private val getHdKeyAccountUseCase: GetHdKeyAccountUseCase,*/
    private val getAccountsCustomInfo: GetAccountsCustomInfo,
    private val getAccountRegistrationTypeUseCase: GetAccountRegistrationTypeUseCase,
    private val getLocalAccountUseCase: GetLocalAccountUseCase,
    private val deleteHdKeyAccountUseCase: DeleteHdKeyAccountUseCase,
    private val deleteAlgo25AccountUseCase: DeleteAlgo25AccountUseCase
) {

    suspend fun addNewAccount(accountCreation: AccountCreation) {
        accountAdditionUseCase.addNewAccount(accountCreation)
    }

    suspend fun getAccount(): List<LocalAccount> {
        return getLocalAccountUseCase()
    }

    suspend fun getAccountLite(): List<AccountLite> {
        val localAccounts = getLocalAccountUseCase()
        val customInfoMap = getAccountsCustomInfo(localAccounts.map { it.algoAddress })
        return localAccounts.map { account ->
            AccountLite(
                account.algoAddress,
                customInfoMap[account.algoAddress]?.customName ?: "",
                getAccountRegistrationTypeUseCase(account)
            )
        }
    }

    suspend fun deleteHdKeyAccount(address: String) {
        deleteHdKeyAccountUseCase(address)
    }

    suspend fun deleteAccount(address: String) {
        deleteAlgo25AccountUseCase(address)
    }
}
