package com.michaeltchuang.walletsdk.account.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hd_keys",
    indices = [
        Index(value = ["public_key"], unique = true),
    ],
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
    val derivationType: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as HdKeyEntity

        if (seedId != other.seedId) return false
        if (account != other.account) return false
        if (change != other.change) return false
        if (keyIndex != other.keyIndex) return false
        if (derivationType != other.derivationType) return false
        if (algoAddress != other.algoAddress) return false
        if (!publicKey.contentEquals(other.publicKey)) return false
        if (!encryptedPrivateKey.contentEquals(other.encryptedPrivateKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = seedId
        result = 31 * result + account
        result = 31 * result + change
        result = 31 * result + keyIndex
        result = 31 * result + derivationType
        result = 31 * result + algoAddress.hashCode()
        result = 31 * result + publicKey.contentHashCode()
        result = 31 * result + encryptedPrivateKey.contentHashCode()
        return result
    }
}
