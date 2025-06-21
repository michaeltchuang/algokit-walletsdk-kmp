package com.michaeltchuang.walletsdk.algosdk

import com.michaeltchuang.walletsdk.algosdk.domain.usecase.RecoverWithPassphrasePreviewUseCase
import org.koin.core.context.GlobalContext.get

internal actual fun getSeedFromEntropy(entropy: ByteArray): ByteArray {
    return ByteArray(0)
}
internal actual fun getEntropyFromMnemonic(mnemonic: String): String {
    val useCase: RecoverWithPassphrasePreviewUseCase = get().get()
    return useCase.getAccount(mnemonic)
}
internal actual fun getMnemonicFromEntropy(entropy: ByteArray): String {
    return ""
}
