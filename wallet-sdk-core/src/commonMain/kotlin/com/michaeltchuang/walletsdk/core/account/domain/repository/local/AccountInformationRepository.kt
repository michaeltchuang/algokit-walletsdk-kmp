package com.michaeltchuang.walletsdk.core.account.domain.repository.local

internal interface AccountInformationRepository {
    suspend fun getRekeyAuthAddress(address: String): String?
}
