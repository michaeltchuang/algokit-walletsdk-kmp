package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.Falcon24Entity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal class Falcon24EntityMapperImpl(
    // private val aesPlatformManager: AESPlatformManager
) : Falcon24EntityMapper {
    override fun invoke(
        localAccount: LocalAccount.Falcon24,
        seedId: Int,
        privateKey: ByteArray,
    ): Falcon24Entity =
        Falcon24Entity(
            algoAddress = localAccount.algoAddress,
            seedId = seedId,
            publicKey = localAccount.publicKey,
            encryptedSecretKey = /*aesPlatformManager.encryptByteArray(privateKey)*/ privateKey,
        )
}
