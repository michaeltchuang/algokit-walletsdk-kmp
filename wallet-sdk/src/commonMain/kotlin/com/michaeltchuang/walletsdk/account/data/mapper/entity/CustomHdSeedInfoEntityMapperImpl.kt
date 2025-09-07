package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.CustomHdSeedInfoEntity
import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomHdSeedInfo


internal class CustomHdSeedInfoEntityMapperImpl :
    CustomHdSeedInfoEntityMapper {
    override fun invoke(info: CustomHdSeedInfo): CustomHdSeedInfoEntity {
        return CustomHdSeedInfoEntity(
            seedId = info.seedId,
            entropyCustomName = info.entropyCustomName,
            orderIndex = info.orderIndex,
            isBackedUp = info.isBackedUp
        )
    }
}