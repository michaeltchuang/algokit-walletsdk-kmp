package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.Falcon24Entity
import com.michaeltchuang.walletsdk.account.domain.model.local.HdWalletSummary

internal interface Falcon24WalletSummaryMapper {
    operator fun invoke(
        entity: Falcon24Entity,
        accountCount: Int,
    ): HdWalletSummary
}
