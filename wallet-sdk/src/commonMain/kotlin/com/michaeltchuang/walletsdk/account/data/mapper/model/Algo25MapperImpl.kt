package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.Algo25Entity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal class Algo25MapperImpl : Algo25Mapper {
    override fun invoke(entity: Algo25Entity): LocalAccount.Algo25 =
        LocalAccount.Algo25(
            algoAddress = entity.algoAddress,
        )
}
