package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.LedgerBleEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal class LedgerBleEntityMapperImpl : LedgerBleEntityMapper {
    override fun invoke(localAccount: LocalAccount.LedgerBle): LedgerBleEntity =
        LedgerBleEntity(
            algoAddress = localAccount.algoAddress,
            deviceMacAddress = localAccount.deviceMacAddress,
            accountIndexInLedger = localAccount.indexInLedger,
            bluetoothName = localAccount.bluetoothName,
        )
}
