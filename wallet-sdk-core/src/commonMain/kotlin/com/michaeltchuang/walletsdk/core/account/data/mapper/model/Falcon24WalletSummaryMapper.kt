package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.Falcon24Entity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.HdWalletSummary

internal interface Falcon24WalletSummaryMapper {
    operator fun invoke(
        entity: Falcon24Entity,
        accountCount: Int,
    ): HdWalletSummary
}
