package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.CustomHdSeedInfoEntity
import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomHdSeedInfo

internal interface CustomHdSeedInfoMapper {
    operator fun invoke(entity: CustomHdSeedInfoEntity): CustomHdSeedInfo
}
