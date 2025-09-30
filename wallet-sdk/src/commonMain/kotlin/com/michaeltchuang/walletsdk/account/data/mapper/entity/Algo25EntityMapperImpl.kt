package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.Algo25Entity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal class Algo25EntityMapperImpl(
    // private val aesPlatformManager: AESPlatformManager
) : Algo25EntityMapper {
    override fun invoke(
        localAccount: LocalAccount.Algo25,
        privateKey: ByteArray,
    ): Algo25Entity =
        Algo25Entity(
            algoAddress = localAccount.algoAddress,
            encryptedSecretKey = /*aesPlatformManager.encryptByteArray(privateKey)*/ privateKey,
        )
}
