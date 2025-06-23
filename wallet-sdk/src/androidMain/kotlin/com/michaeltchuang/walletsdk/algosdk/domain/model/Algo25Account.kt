

package com.michaeltchuang.walletsdk.algosdk.domain.model

data class Algo25Account(
    val address: String,
    val secretKey: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Algo25Account

        if (address != other.address) return false
        if (!secretKey.contentEquals(other.secretKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = address.hashCode()
        result = 31 * result + secretKey.contentHashCode()
        return result
    }
}
