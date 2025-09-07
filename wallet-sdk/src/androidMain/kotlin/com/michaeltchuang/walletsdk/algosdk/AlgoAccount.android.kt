package com.michaeltchuang.walletsdk.algosdk

import com.michaeltchuang.walletsdk.algosdk.domain.model.Algo25Account
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoAccountSdkImpl

actual fun createAlgo25Account(): Algo25Account? {
    val algoAccountSdkImpl = AlgoAccountSdkImpl()
    return algoAccountSdkImpl.createAlgo25Account()
}

actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray) {}
