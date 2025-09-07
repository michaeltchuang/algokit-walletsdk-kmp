package com.michaeltchuang.walletsdk.account.domain.repository.local

internal interface Algo25NoAuthRepository {
    suspend fun updateInvalidAlgo25AccountsToNoAuth()
}
