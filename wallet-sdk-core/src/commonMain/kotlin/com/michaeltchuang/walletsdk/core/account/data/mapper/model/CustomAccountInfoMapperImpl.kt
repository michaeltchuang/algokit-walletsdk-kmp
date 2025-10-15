package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.CustomAccountInfoEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomAccountInfo

internal class CustomAccountInfoMapperImpl : CustomAccountInfoMapper {
    override fun invoke(
        address: String,
        customAccountInfoEntity: CustomAccountInfoEntity?,
    ): CustomAccountInfo =
        CustomAccountInfo(
            address = address,
            customName = customAccountInfoEntity?.customName,
            orderIndex = customAccountInfoEntity?.orderIndex ?: 0,
            isBackedUp = customAccountInfoEntity?.isBackedUp ?: false,
        )
}
