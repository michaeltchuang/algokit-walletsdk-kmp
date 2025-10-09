package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomHdSeedInfo
import com.michaeltchuang.walletsdk.account.domain.repository.custom.CustomHdSeedInfoRepository
import com.michaeltchuang.walletsdk.account.domain.repository.local.HdSeedRepository
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetSeedIdIfExistingEntropy
import com.michaeltchuang.walletsdk.algosdk.getSeedFromEntropy
import com.michaeltchuang.walletsdk.foundation.AlgoKitResult
import com.michaeltchuang.walletsdk.foundation.utils.clearFromMemory

internal class AddHdSeedUseCase(
    private val hdSeedRepository: HdSeedRepository,
    private val customHdSeedInfoRepository: CustomHdSeedInfoRepository,
    private val getSeedIdIfExistingEntropy: GetSeedIdIfExistingEntropy,
) : AddHdSeed {
    override suspend fun invoke(entropy: ByteArray): AlgoKitResult<Int> {
        val existingSeedId = getSeedIdIfExistingEntropy.invoke(entropy)
        return if (existingSeedId != null) {
            AlgoKitResult.Success(existingSeedId)
        } else {
            createNewSeed(entropy)
        }
    }

    private suspend fun createNewSeed(entropy: ByteArray): AlgoKitResult<Int> {
        val seed = getSeedFromEntropy(entropy) ?: return AlgoKitResult.Error(Exception("Failed to generate seed from entropy"))
        val newSeedId = addHdSeed(seed.copyOf(), entropy)
        setCustomInfo(newSeedId)
        seed.clearFromMemory()
        return AlgoKitResult.Success(newSeedId)
    }

    private suspend fun addHdSeed(
        seed: ByteArray,
        entropy: ByteArray,
    ): Int =
        hdSeedRepository
            .addHdSeed(
                seedId = 0, // ID will be auto-generated
                seed = seed,
                entropy = entropy,
            ).toInt()

    private suspend fun setCustomInfo(seedId: Int) {
        customHdSeedInfoRepository.setCustomInfo(
            CustomHdSeedInfo(
                seedId = seedId,
                entropyCustomName = "Wallet #$seedId",
                orderIndex = seedId,
                isBackedUp = false,
            ),
        )
    }
}
