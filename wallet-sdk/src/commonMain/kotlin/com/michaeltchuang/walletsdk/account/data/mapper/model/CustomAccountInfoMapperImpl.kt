package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.CustomAccountInfoEntity
import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomAccountInfo

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
