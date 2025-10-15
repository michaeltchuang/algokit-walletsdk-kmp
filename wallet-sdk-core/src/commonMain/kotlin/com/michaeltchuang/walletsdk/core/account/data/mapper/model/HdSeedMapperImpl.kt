package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.HdSeedEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.HdSeed

internal class HdSeedMapperImpl : HdSeedMapper {
    override fun invoke(entity: HdSeedEntity): HdSeed =
        HdSeed(
            seedId = entity.seedId,
        )
}
