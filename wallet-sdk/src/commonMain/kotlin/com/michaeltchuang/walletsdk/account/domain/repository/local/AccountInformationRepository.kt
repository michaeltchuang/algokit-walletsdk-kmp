package com.michaeltchuang.walletsdk.account.domain.repository.local

internal interface AccountInformationRepository {
    suspend fun getRekeyAuthAddress(address: String): String?
}
