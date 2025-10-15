package com.michaeltchuang.walletsdk.core.account.domain.repository.local

internal interface Algo25NoAuthRepository {
    suspend fun updateInvalidAlgo25AccountsToNoAuth()
}
