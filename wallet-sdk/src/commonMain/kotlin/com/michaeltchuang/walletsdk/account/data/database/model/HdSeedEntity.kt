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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as HdSeedEntity

        if (seedId != other.seedId) return false
        if (!encryptedEntropy.contentEquals(other.encryptedEntropy)) return false
        if (!encryptedSeed.contentEquals(other.encryptedSeed)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = seedId
        result = 31 * result + encryptedEntropy.contentHashCode()
        result = 31 * result + encryptedSeed.contentHashCode()
        return result
    }
}
