package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.CustomAccountInfoEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomAccountInfo

internal interface CustomAccountInfoMapper {
    operator fun invoke(
        address: String,
        customAccountInfoEntity: CustomAccountInfoEntity?,
    ): CustomAccountInfo
}
