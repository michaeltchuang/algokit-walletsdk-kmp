package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.core.algosdk.bip39.model.Falcon24

interface AccountCreationFalcon24TypeMapper {
    operator fun invoke(
        entropy: ByteArray,
        falcon24: Falcon24,
        seedId: Int?,
    ): AccountCreation.Type.Falcon24
}
