package com.michaeltchuang.walletsdk.core.account.domain.repository.local

import com.michaeltchuang.walletsdk.core.account.domain.model.local.HdSeed
import kotlinx.coroutines.flow.Flow

interface HdSeedRepository {
    fun getAllAsFlow(): Flow<List<HdSeed>>

    fun getSeedCountAsFlow(): Flow<Int>

    suspend fun getHdSeedCount(): Int

    suspend fun getMaxSeedId(): Int?

    suspend fun hasAnySeed(): Boolean

    suspend fun getSeedIdIfExistingEntropy(entropy: ByteArray): Int?

    suspend fun getAllHdSeeds(): List<HdSeed>

    suspend fun getHdSeed(seedId: Int): HdSeed?

    suspend fun addHdSeed(
        seedId: Int,
        entropy: ByteArray,
        seed: ByteArray,
    ): Long

    suspend fun deleteHdSeed(seedId: Int)

    suspend fun deleteAllHdSeeds()

    suspend fun getEntropy(seedId: Int): ByteArray?

    suspend fun getSeed(seedId: Int): ByteArray?
}
