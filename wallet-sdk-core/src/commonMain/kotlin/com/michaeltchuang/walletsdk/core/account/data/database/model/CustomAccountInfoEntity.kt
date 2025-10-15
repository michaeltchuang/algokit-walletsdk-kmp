package com.michaeltchuang.walletsdk.core.account.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_account_info")
internal data class CustomAccountInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "algo_address")
    val algoAddress: String,
    @ColumnInfo(name = "custom_name")
    val customName: String?,
    @ColumnInfo(name = "order_index")
    val orderIndex: Int,
    @ColumnInfo(name = "is_backed_up")
    val isBackedUp: Boolean,
)
