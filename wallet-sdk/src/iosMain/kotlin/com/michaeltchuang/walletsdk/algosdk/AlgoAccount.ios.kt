package com.michaeltchuang.walletsdk.algosdk

import AlgorandXhdIosSdk.xHdSwiftBridge
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
val contentFromSwift = xHdSwiftBridge().toMD5WithValue(value = "someString")

internal actual fun createAlgo25Account() {

}

internal actual fun recoverAlgo25Account(mnemonic: String) {}

internal actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray) {}
