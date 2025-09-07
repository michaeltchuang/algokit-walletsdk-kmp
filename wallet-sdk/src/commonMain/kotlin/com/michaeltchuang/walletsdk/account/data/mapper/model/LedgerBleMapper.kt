package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.LedgerBleEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal interface LedgerBleMapper {
    operator fun invoke(entity: LedgerBleEntity): LocalAccount.LedgerBle
}
