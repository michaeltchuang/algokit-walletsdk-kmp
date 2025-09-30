package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.CustomAccountInfoEntity
import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomAccountInfo

internal interface CustomAccountInfoMapper {
    operator fun invoke(
        address: String,
        customAccountInfoEntity: CustomAccountInfoEntity?,
    ): CustomAccountInfo
}
