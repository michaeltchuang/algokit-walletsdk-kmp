package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.HdSeedEntity

internal class HdSeedEntityMapperImpl(
    // private val aesPlatformManager: AESPlatformManager
) : HdSeedEntityMapper {
    override fun invoke(
        seedId: Int,
        entropy: ByteArray,
        seed: ByteArray,
    ): HdSeedEntity =
        HdSeedEntity(
            seedId = 0, // Let Room auto-generate the ID
            encryptedEntropy = /*aesPlatformManager.encryptByteArray(entropy)*/entropy,
            encryptedSeed = /*aesPlatformManager.encryptByteArray(seed)*/ seed,
        )
}
