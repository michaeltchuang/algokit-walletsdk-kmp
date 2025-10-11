package com.michaeltchuang.walletsdk.account.domain.usecase.local

internal class IsAccountRekeyedToAnotherAccountUseCase(
    private val getAccountRekeyAdminAddress: GetAccountRekeyAdminAddress,
) : IsAccountRekeyedToAnotherAccount {
    override suspend fun invoke(address: String): Boolean {
        val adminAddress = getAccountRekeyAdminAddress(address)
        return !adminAddress.isNullOrBlank() && adminAddress != address
    }
}
