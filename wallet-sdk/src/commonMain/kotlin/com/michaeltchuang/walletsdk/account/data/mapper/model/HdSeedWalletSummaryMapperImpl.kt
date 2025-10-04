package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.HdSeedEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.HdWalletSummary

internal class HdSeedWalletSummaryMapperImpl : HdSeedWalletSummaryMapper {
    // TODO: set primary and secondary values
    override fun invoke(
        entity: HdSeedEntity,
        accountCount: Int,
    ): HdWalletSummary =
        HdWalletSummary(
            seedId = entity.seedId,
            accountCount = accountCount,
            maxAccountIndex = 0,
            primaryValue = "",
            secondaryValue = "",
        )
}
