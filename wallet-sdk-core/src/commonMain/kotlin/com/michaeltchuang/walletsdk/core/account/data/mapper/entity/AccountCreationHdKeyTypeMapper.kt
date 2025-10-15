package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.core.algosdk.bip39.model.HdKeyAddress

interface AccountCreationHdKeyTypeMapper {
    operator fun invoke(
        entropy: ByteArray,
        hdKeyAddress: HdKeyAddress,
        seedId: Int?,
    ): AccountCreation.Type.HdKey
}
