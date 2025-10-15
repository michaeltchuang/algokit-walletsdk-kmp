package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.Falcon24Entity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount

internal interface Falcon24EntityMapper {
    operator fun invoke(
        localAccount: LocalAccount.Falcon24,
        seedId: Int,
        privateKey: ByteArray,
    ): Falcon24Entity
}
