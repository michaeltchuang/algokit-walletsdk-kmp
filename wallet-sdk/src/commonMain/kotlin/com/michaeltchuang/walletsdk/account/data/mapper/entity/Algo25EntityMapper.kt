package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.Algo25Entity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal interface Algo25EntityMapper {
    operator fun invoke(
        localAccount: LocalAccount.Algo25,
        privateKey: ByteArray,
    ): Algo25Entity
}
