package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.HdSeedEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.HdWalletSummary

internal interface HdSeedWalletSummaryMapper {
    operator fun invoke(
        entity: HdSeedEntity,
        accountCount: Int,
    ): HdWalletSummary
}
