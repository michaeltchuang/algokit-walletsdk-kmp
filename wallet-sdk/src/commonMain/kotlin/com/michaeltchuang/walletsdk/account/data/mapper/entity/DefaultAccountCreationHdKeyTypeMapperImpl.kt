/*
package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddress
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddressDerivationType
import com.michaeltchuang.walletsdk.encryption.domain.manager.AESPlatformManager

internal class DefaultAccountCreationHdKeyTypeMapperImpl(
    private val aesPlatformManager: AESPlatformManager
) : AccountCreationHdKeyTypeMapper {

    override fun invoke(
        entropy: ByteArray,
        hdKeyAddress: HdKeyAddress,
        seedId: Int?
    ): AccountCreation.Type.HdKey {
        return with(hdKeyAddress) {
            AccountCreation.Type.HdKey(
                publicKey.toByteArray(),
                aesPlatformManager.encryptByteArray(privateKey.toByteArray()),
                aesPlatformManager.encryptByteArray(entropy),
                index.accountIndex,
                index.changeIndex,
                index.keyIndex,
                HdKeyAddressDerivationType.Peikert.value,
                seedId
            )
        }
    }
}
*/
