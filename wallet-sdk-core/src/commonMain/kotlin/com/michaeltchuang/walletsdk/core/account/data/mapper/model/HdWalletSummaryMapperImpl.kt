package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.HdKeyEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.HdWalletSummary

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
