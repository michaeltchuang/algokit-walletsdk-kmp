package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.CustomAccountInfoEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomAccountInfo

internal interface CustomAccountInfoEntityMapper {
    operator fun invoke(customAccountInfo: CustomAccountInfo): CustomAccountInfoEntity
}
