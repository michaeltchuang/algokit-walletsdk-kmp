package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.CustomAccountInfoEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomAccountInfo

internal class CustomAccountInfoEntityMapperImpl : CustomAccountInfoEntityMapper {
    override fun invoke(customAccountInfo: CustomAccountInfo): CustomAccountInfoEntity =
        CustomAccountInfoEntity(
            algoAddress = customAccountInfo.address,
            customName = customAccountInfo.customName,
            orderIndex = customAccountInfo.orderIndex,
            isBackedUp = customAccountInfo.isBackedUp,
        )
}
