package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.HdKeyEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.HdWalletSummary

internal interface HdWalletSummaryMapper {
    operator fun invoke(
        entity: HdKeyEntity,
        accountCount: Int,
    ): HdWalletSummary
}
