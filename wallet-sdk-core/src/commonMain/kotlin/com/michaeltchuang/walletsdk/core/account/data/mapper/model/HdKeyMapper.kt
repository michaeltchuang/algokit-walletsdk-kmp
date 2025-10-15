package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.HdKeyEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount

internal interface HdKeyMapper {
    operator fun invoke(entity: HdKeyEntity): LocalAccount.HdKey
}
