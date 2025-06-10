 

package com.michaeltchuang.walletsdk.algosdk.domain.model

data class HdKeyAccount(
    val address: String,
    val publicKey: ByteArray,
    val privateKey: ByteArray,
    val entropy: ByteArray,
    val account: Int,
    val change: Int,
    val keyIndex: Int,
    val derivationType: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as HdKeyAccount

        if (address != other.address) return false
        if (!privateKey.contentEquals(other.privateKey)) return false
        if (!entropy.contentEquals(other.entropy)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = address.hashCode()
        result = 31 * result + privateKey.hashCode()
        result = 31 * result + entropy.hashCode()
        return result
    }
}
