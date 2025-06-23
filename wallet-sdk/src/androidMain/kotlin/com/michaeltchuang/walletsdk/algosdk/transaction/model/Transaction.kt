

package com.michaeltchuang.walletsdk.algosdk.transaction.model

interface Transaction {
    val signerAddress: String
    val value: ByteArray

    data class AssetTransaction(
        override val signerAddress: String,
        override val value: ByteArray,
    ) : Transaction

    data class AlgoTransaction(
        override val signerAddress: String,
        override val value: ByteArray,
    ) : Transaction

    data class RekeyTransaction(
        override val signerAddress: String,
        override val value: ByteArray,
    ) : Transaction

    data class AddAssetTransaction(
        override val signerAddress: String,
        override val value: ByteArray,
    ) : Transaction

    data class RemoveAssetTransaction(
        override val signerAddress: String,
        override val value: ByteArray,
    ) : Transaction

    data class SendAndRemoveAssetTransaction(
        override val signerAddress: String,
        override val value: ByteArray,
    ) : Transaction
}
