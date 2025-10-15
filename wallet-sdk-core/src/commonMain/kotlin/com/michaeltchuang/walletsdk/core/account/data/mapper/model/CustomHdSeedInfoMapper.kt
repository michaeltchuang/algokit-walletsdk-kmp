package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.CustomHdSeedInfoEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomHdSeedInfo

internal interface CustomHdSeedInfoMapper {
    operator fun invoke(entity: CustomHdSeedInfoEntity): CustomHdSeedInfo
}
