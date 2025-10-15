package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.Algo25Entity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount

internal interface Algo25Mapper {
    operator fun invoke(entity: Algo25Entity): LocalAccount.Algo25
}
