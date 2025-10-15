package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.Falcon24Entity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.HdWalletSummary

internal class Falcon24WalletSummaryMapperImpl : Falcon24WalletSummaryMapper {
    // TODO: set primary and secondary values
    override fun invoke(
        entity: Falcon24Entity,
        accountCount: Int,
    ): HdWalletSummary =
        HdWalletSummary(
            seedId = entity.seedId,
            accountCount = accountCount,
            maxAccountIndex = 1,
            primaryValue = "",
            secondaryValue = "",
        )
}
