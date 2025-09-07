package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.HdSeedEntity

internal class HdSeedEntityMapperImpl(
   /* private val aesPlatformManager: AESPlatformManager*/
) : HdSeedEntityMapper {

    override fun invoke(seedId: Int, entropy: ByteArray, seed: ByteArray): HdSeedEntity {
        return HdSeedEntity(
            seedId = 0, // Let Room auto-generate the ID
            encryptedEntropy = /*aesPlatformManager.encryptByteArray(entropy)*/entropy,
            encryptedSeed = /*aesPlatformManager.encryptByteArray(seed)*/ seed
        )
    }
}
