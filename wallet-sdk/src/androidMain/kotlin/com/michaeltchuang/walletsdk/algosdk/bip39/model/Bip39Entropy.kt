package com.michaeltchuang.walletsdk.algosdk.bip39.model

data class Bip39Entropy internal constructor(val value: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bip39Entropy

        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }
}
