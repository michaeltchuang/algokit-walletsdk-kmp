package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.Falcon24Entity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount

internal interface Falcon24Mapper {
    operator fun invoke(entity: Falcon24Entity): LocalAccount.Falcon24
}
