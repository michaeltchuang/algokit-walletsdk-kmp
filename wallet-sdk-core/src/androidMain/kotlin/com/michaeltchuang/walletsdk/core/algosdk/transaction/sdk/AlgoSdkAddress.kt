package com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk

import com.michaeltchuang.walletsdk.core.algosdk.transaction.model.AlgorandAddress

interface AlgoSdkAddress {
    fun isValid(address: String): Boolean

    fun generateAddressFromPublicKey(publicKey: ByteArray): AlgorandAddress?

    fun generateAddressFromPublicKey(addressBase64: String): AlgorandAddress?
}
