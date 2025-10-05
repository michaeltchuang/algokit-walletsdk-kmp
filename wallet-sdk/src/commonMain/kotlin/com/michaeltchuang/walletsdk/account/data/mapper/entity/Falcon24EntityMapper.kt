package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.Falcon24Entity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal interface Falcon24EntityMapper {
    operator fun invoke(
        localAccount: LocalAccount.Falcon24,
        seedId: Int,
        privateKey: ByteArray,
    ): Falcon24Entity
}
