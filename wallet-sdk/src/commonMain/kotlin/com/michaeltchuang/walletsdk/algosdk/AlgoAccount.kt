package com.michaeltchuang.walletsdk.algosdk

internal expect fun createAlgo25Account()
internal expect fun recoverAlgo25Account(mnemonic: String)
internal expect fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray)
