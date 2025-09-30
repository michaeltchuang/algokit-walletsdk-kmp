package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.HdKeyEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal interface HdKeyMapper {
    operator fun invoke(entity: HdKeyEntity): LocalAccount.HdKey
}
