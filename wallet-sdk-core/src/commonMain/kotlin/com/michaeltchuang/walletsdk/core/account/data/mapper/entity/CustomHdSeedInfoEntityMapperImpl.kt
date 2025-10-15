package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.CustomHdSeedInfoEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomHdSeedInfo

internal class CustomHdSeedInfoEntityMapperImpl : CustomHdSeedInfoEntityMapper {
    override fun invoke(info: CustomHdSeedInfo): CustomHdSeedInfoEntity =
        CustomHdSeedInfoEntity(
            seedId = info.seedId,
            entropyCustomName = info.entropyCustomName,
            orderIndex = info.orderIndex,
            isBackedUp = info.isBackedUp,
        )
}
