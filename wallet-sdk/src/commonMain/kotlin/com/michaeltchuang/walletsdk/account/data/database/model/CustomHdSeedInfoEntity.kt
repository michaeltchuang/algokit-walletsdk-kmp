package com.michaeltchuang.walletsdk.account.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_hd_seed_info")
internal data class CustomHdSeedInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "seed_id")
    val seedId: Int,
    @ColumnInfo("entropy_custom_name")
    val entropyCustomName: String,
    @ColumnInfo(name = "order_index")
    val orderIndex: Int,
    @ColumnInfo(name = "is_backed_up")
    val isBackedUp: Boolean,
)
