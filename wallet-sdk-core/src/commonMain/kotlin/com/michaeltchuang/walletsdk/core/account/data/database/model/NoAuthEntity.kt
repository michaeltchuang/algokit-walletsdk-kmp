package com.michaeltchuang.walletsdk.core.account.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "no_auth")
internal data class NoAuthEntity(
    @PrimaryKey
    @ColumnInfo("algo_address")
    val algoAddress: String,
)
