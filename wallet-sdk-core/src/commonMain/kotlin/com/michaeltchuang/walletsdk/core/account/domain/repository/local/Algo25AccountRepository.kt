package com.michaeltchuang.walletsdk.core.account.domain.repository.local

import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount
import kotlinx.coroutines.flow.Flow

interface Algo25AccountRepository {
    fun getAllAsFlow(): Flow<List<LocalAccount.Algo25>>

    fun getAccountCountAsFlow(): Flow<Int>

    suspend fun getAccountCount(): Int

    suspend fun getAll(): List<LocalAccount.Algo25>

    suspend fun getAllAddresses(): List<String>

    suspend fun getAccount(address: String): LocalAccount.Algo25?

    suspend fun addAccount(
        account: LocalAccount.Algo25,
        privateKey: ByteArray,
    )

    suspend fun deleteAccount(address: String)

    suspend fun deleteAllAccounts()

    suspend fun getSecretKey(address: String): ByteArray?
}
