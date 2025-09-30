package com.michaeltchuang.walletsdk.account.domain.model.local

sealed interface LocalAccount {
    val algoAddress: String

    data class HdKey(
        override val algoAddress: String,
        val publicKey: ByteArray,
        val seedId: Int,
        val account: Int,
        val change: Int,
        val keyIndex: Int,
        val derivationType: Int,
    ) : LocalAccount {
        override fun equals(other: Any?): Boolean =
            other is HdKey &&
                algoAddress == other.algoAddress &&
                publicKey.contentEquals(other.publicKey) &&
                seedId == other.seedId &&
                account == other.account &&
                change == other.change &&
                keyIndex == other.keyIndex &&
                derivationType == other.derivationType

        override fun hashCode(): Int =
            algoAddress.hashCode() + publicKey.contentHashCode() + seedId + account + change + keyIndex + derivationType
    }

    data class Algo25(
        override val algoAddress: String,
    ) : LocalAccount

    data class LedgerBle(
        override val algoAddress: String,
        val deviceMacAddress: String,
        val bluetoothName: String?,
        val indexInLedger: Int,
    ) : LocalAccount

    data class NoAuth(
        override val algoAddress: String,
    ) : LocalAccount
}
