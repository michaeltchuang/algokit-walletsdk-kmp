package com.michaeltchuang.walletsdk.account.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hd_keys",
    indices = [
        Index(value = ["public_key"], unique = true)
    ]
)
internal data class HdKeyEntity(
    @PrimaryKey
    @ColumnInfo("algo_address")
    val algoAddress: String,

    @ColumnInfo("public_key", typeAffinity = ColumnInfo.BLOB)
    val publicKey: ByteArray,

    @ColumnInfo("encrypted_private_key", typeAffinity = ColumnInfo.BLOB)
    val encryptedPrivateKey: ByteArray,

    @ColumnInfo("seed_id")
    val seedId: Int,

    @ColumnInfo("account")
    val account: Int,

    @ColumnInfo("change")
    val change: Int,

    @ColumnInfo("key_index")
    val keyIndex: Int,

    @ColumnInfo("derivation_type")
    val derivationType: Int
)
