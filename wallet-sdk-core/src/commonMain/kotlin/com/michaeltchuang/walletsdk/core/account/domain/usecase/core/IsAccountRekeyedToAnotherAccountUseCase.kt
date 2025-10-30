package com.michaeltchuang.walletsdk.core.account.domain.usecase.core

import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetAccountRekeyAdminAddress
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.IsAccountRekeyedToAnotherAccount

internal class IsAccountRekeyedToAnotherAccountUseCase(
    private val getAccountRekeyAdminAddress: GetAccountRekeyAdminAddress,
) : IsAccountRekeyedToAnotherAccount {
    override suspend fun invoke(address: String): Boolean {
        val adminAddress = getAccountRekeyAdminAddress(address)
        return !adminAddress.isNullOrBlank() && adminAddress != address
    }
}
