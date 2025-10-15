package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.HdSeedEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.HdSeed

internal interface HdSeedMapper {
    operator fun invoke(entity: HdSeedEntity): HdSeed
}
