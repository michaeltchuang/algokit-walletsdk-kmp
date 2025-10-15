package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.LedgerBleEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount

internal interface LedgerBleEntityMapper {
    operator fun invoke(localAccount: LocalAccount.LedgerBle): LedgerBleEntity
}
