package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.CustomHdSeedInfoEntity
import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomHdSeedInfo


internal class CustomHdSeedInfoMapperImpl : CustomHdSeedInfoMapper {
    override fun invoke(entity: CustomHdSeedInfoEntity): CustomHdSeedInfo {
        return CustomHdSeedInfo(
            seedId = entity.seedId,
            entropyCustomName = entity.entropyCustomName,
            orderIndex = entity.orderIndex,
            isBackedUp = entity.isBackedUp
        )
    }
}