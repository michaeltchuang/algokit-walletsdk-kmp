package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.HdKeyEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.HdWalletSummary

internal class HdWalletSummaryMapperImpl : HdWalletSummaryMapper {
    // TODO: set primary and secondary values
    override fun invoke(
        entity: HdKeyEntity,
        accountCount: Int,
    ): HdWalletSummary =
        HdWalletSummary(
            seedId = entity.seedId,
            accountCount = accountCount,
            maxAccountIndex = entity.account,
            primaryValue = "",
            secondaryValue = "",
        )
}
