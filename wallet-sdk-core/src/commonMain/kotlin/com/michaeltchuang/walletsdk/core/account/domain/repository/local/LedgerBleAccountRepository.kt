package com.michaeltchuang.walletsdk.core.account.domain.repository.local

import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount
import kotlinx.coroutines.flow.Flow

internal interface LedgerBleAccountRepository {
    fun getAllAsFlow(): Flow<List<LocalAccount.LedgerBle>>

    fun getAccountCountAsFlow(): Flow<Int>

    suspend fun getAccountCount(): Int

    suspend fun getAll(): List<LocalAccount.LedgerBle>

    suspend fun getAllAddresses(): List<String>

    suspend fun getAccount(address: String): LocalAccount.LedgerBle?

    suspend fun addAccount(account: LocalAccount.LedgerBle)

    suspend fun deleteAccount(address: String)

    suspend fun deleteAllAccounts()
}
