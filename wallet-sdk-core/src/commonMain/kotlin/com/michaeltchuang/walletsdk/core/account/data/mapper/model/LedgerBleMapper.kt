package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.LedgerBleEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount

internal interface LedgerBleMapper {
    operator fun invoke(entity: LedgerBleEntity): LocalAccount.LedgerBle
}
