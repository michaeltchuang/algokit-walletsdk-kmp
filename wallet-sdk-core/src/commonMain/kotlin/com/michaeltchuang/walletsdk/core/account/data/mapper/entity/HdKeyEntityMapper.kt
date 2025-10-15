package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.HdKeyEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount

internal interface HdKeyEntityMapper {
    operator fun invoke(
        localAccount: LocalAccount.HdKey,
        privateKey: ByteArray,
    ): HdKeyEntity
}
