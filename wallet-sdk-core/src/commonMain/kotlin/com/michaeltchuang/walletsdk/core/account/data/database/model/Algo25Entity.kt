package com.michaeltchuang.walletsdk.core.account.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "algo_25")
internal data class Algo25Entity(
    @PrimaryKey
    @ColumnInfo("algo_address")
    val algoAddress: String,
    @ColumnInfo("encrypted_secret_key", typeAffinity = ColumnInfo.BLOB)
    val encryptedSecretKey: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        // if (javaClass != other?.javaClass) return false

        other as Algo25Entity

        if (algoAddress != other.algoAddress) return false
        if (!encryptedSecretKey.contentEquals(other.encryptedSecretKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = algoAddress.hashCode()
        result = 31 * result + encryptedSecretKey.contentHashCode()
        return result
    }
}
