package com.michaeltchuang.walletsdk.algosdk.bip39.model

data class HdKeyAddress(
    val address: String,
    val index: HdKeyAddressIndex,
    val privateKey: ByteArray,
    val publicKey: ByteArray,
    val derivationType: HdKeyAddressDerivationType,
)
