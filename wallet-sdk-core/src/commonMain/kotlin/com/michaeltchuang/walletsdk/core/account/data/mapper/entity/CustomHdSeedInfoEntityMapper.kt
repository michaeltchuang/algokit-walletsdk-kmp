package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.CustomHdSeedInfoEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomHdSeedInfo

internal interface CustomHdSeedInfoEntityMapper {
    operator fun invoke(customHdSeedInfo: CustomHdSeedInfo): CustomHdSeedInfoEntity
}
