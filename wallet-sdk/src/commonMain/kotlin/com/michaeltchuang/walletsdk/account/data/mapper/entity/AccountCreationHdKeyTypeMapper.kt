package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddress

interface AccountCreationHdKeyTypeMapper {
    operator fun invoke(
        entropy: ByteArray,
        hdKeyAddress: HdKeyAddress,
        seedId: Int?,
    ): AccountCreation.Type.HdKey
}
