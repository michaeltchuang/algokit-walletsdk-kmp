package com.michaeltchuang.walletsdk.algosdk

import org.koin.core.context.GlobalContext.get

internal actual fun createAlgo25Account() {}

internal actual fun recoverAlgo25Account(mnemonic: String) {
//    val useCase: RecoverWithPassphrasePreviewUseCase = get().get()
//    return useCase.getAccount(mnemonic)
}

internal actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray) {}
