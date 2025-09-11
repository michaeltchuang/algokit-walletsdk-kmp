package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.CustomHdSeedInfoEntity
import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomHdSeedInfo


internal interface CustomHdSeedInfoEntityMapper {
    operator fun invoke(customHdSeedInfo: CustomHdSeedInfo): CustomHdSeedInfoEntity
}
