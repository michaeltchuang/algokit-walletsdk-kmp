/*
 * Copyright 2022-2025 Pera Wallet, LDA
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.michaeltchuang.walletsdk.algosdk.domain.usecase

import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39Sdk

class RecoverWithPassphrasePreviewUseCase(
    private val peraBip39Sdk: AlgoKitBip39Sdk,
) {
    fun getAccount(
        mnemonics: String,
    ): String {
        val entropy = peraBip39Sdk.getEntropyFromMnemonic(mnemonics)
        return entropy.contentToString()
    }
}
