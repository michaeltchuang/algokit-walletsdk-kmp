package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.HdSeedEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.HdSeed

internal class HdSeedMapperImpl : HdSeedMapper {
    override fun invoke(entity: HdSeedEntity): HdSeed =
        HdSeed(
            seedId = entity.seedId,
        )
}
