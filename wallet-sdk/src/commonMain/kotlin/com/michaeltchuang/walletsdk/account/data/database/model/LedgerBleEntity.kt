package com.michaeltchuang.walletsdk.account.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ledger_ble")
internal data class LedgerBleEntity(
    @PrimaryKey
    @ColumnInfo("algo_address")
    val algoAddress: String,

    @ColumnInfo("device_mac_address")
    val deviceMacAddress: String,

    @ColumnInfo("account_index_in_ledger")
    val accountIndexInLedger: Int,

    @ColumnInfo("bluetooth_name")
    val bluetoothName: String?
)
