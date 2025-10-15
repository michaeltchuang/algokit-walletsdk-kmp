package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.HdSeedEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.HdWalletSummary

internal interface HdSeedWalletSummaryMapper {
    operator fun invoke(
        entity: HdSeedEntity,
        accountCount: Int,
    ): HdWalletSummary
}
