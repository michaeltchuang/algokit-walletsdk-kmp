package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.HdKeyEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.HdWalletSummary

internal interface HdWalletSummaryMapper {
    operator fun invoke(
        entity: HdKeyEntity,
        accountCount: Int,
    ): HdWalletSummary
}
