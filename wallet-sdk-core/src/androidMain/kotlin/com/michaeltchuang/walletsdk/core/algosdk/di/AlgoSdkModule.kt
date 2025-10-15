package com.michaeltchuang.walletsdk.core.algosdk.di

import com.michaeltchuang.walletsdk.core.algosdk.domain.usecase.RecoverWithPassphrasePreviewUseCase
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.AlgoAccountSdkImpl
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.AlgoKitBip39Sdk
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.AlgoKitBip39SdkImpl
import org.koin.dsl.module

val algoSdkModule =
    module {
        single { AlgoAccountSdkImpl() }
        single<AlgoKitBip39Sdk> { AlgoKitBip39SdkImpl() }
        factory { RecoverWithPassphrasePreviewUseCase(get()) }
    }
