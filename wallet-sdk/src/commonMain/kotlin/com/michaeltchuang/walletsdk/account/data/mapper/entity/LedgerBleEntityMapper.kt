package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.LedgerBleEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal interface LedgerBleEntityMapper {

    operator fun invoke(localAccount: LocalAccount.LedgerBle): LedgerBleEntity
}
