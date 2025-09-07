package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.CustomAccountInfoEntity
import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomAccountInfo


internal interface CustomAccountInfoEntityMapper {
    operator fun invoke(customAccountInfo: CustomAccountInfo): CustomAccountInfoEntity
}
