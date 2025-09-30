package com.michaeltchuang.walletsdk.account.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hd_seeds",
    indices = [
        Index(value = ["encrypted_entropy"], unique = true),
        Index(value = ["encrypted_seed"], unique = true),
    ],
)
internal data class HdSeedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("seed_id")
    val seedId: Int,
    @ColumnInfo("encrypted_entropy", typeAffinity = ColumnInfo.BLOB)
    val encryptedEntropy: ByteArray,
    @ColumnInfo("encrypted_seed", typeAffinity = ColumnInfo.BLOB)
    val encryptedSeed: ByteArray,
)
