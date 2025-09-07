package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.HdKeyEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal interface HdKeyEntityMapper {
    operator fun invoke(
        localAccount: LocalAccount.HdKey,
        privateKey: ByteArray
    ): HdKeyEntity
}
