package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.LedgerBleEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal class LedgerBleMapperImpl : LedgerBleMapper {
    override fun invoke(entity: LedgerBleEntity): LocalAccount.LedgerBle =
        LocalAccount.LedgerBle(
            algoAddress = entity.algoAddress,
            deviceMacAddress = entity.deviceMacAddress,
            indexInLedger = entity.accountIndexInLedger,
            bluetoothName = entity.bluetoothName,
        )
}
