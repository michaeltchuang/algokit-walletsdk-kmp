package com.michaeltchuang.walletsdk.account.domain.repository.local

import com.michaeltchuang.walletsdk.account.domain.model.local.HdWalletSummary
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import kotlinx.coroutines.flow.Flow

interface HdKeyAccountRepository {

    fun getAllAsFlow(): Flow<List<LocalAccount.HdKey>>

    fun getAccountCountAsFlow(): Flow<Int>

    suspend fun getAccountCount(): Int

    suspend fun getAll(): List<LocalAccount.HdKey>

    suspend fun getAllAddresses(): List<String>

    suspend fun getAccount(address: String): LocalAccount.HdKey?

    suspend fun getDerivedAddressCountOfSeed(seedId: Int): Int

    suspend fun addAccount(
        account: LocalAccount.HdKey,
        privateKey: ByteArray
    )

    suspend fun deleteAccount(address: String)

    suspend fun deleteAllAccounts()

    suspend fun getPrivateKey(address: String): ByteArray?

    suspend fun getHdWalletSummaries(): List<HdWalletSummary>

    suspend fun getHdSeedId(address: String): Int?
}
