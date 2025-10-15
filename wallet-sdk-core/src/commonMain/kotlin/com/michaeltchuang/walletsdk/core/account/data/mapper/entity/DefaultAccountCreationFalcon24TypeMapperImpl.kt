package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.core.algosdk.bip39.model.Falcon24

internal class DefaultAccountCreationFalcon24TypeMapperImpl(
    // private val aesPlatformManager: AESPlatformManager
) : AccountCreationFalcon24TypeMapper {
    override fun invoke(
        entropy: ByteArray,
        falcon24: Falcon24,
        seedId: Int?,
    ): AccountCreation.Type.Falcon24 =
        with(falcon24) {
            AccountCreation.Type.Falcon24(
                // aesPlatformManager.encryptByteArray(privateKey.toByteArray())
                publicKey = publicKey,
                encryptedPrivateKey = privateKey,
                encryptedEntropy = entropy, // aesPlatformManager.encryptByteArray(entropy)
                seedId = seedId,
            )
        }
}
