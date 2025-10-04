package com.michaeltchuang.walletsdk.account.domain.repository.local

import com.michaeltchuang.walletsdk.account.domain.model.local.HdWalletSummary
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import kotlinx.coroutines.flow.Flow

interface Falcon24AccountRepository {
    fun getAllAsFlow(): Flow<List<LocalAccount.Falcon24>>

    fun getAccountCountAsFlow(): Flow<Int>

    suspend fun getAccountCount(): Int

    suspend fun getAll(): List<LocalAccount.Falcon24>

    suspend fun getAllAddresses(): List<String>

    suspend fun getAccount(address: String): LocalAccount.Falcon24?

    suspend fun addAccount(
        account: LocalAccount.Falcon24,
        seedId: Int,
        privateKey: ByteArray,
    )

    suspend fun deleteAccount(address: String)

    suspend fun deleteAllAccounts()

    suspend fun getSecretKey(address: String): ByteArray?

    suspend fun getHdWalletSummaries(): List<HdWalletSummary>

    suspend fun getDerivedAddressCountOfSeed(seedId: Int): Int
}
