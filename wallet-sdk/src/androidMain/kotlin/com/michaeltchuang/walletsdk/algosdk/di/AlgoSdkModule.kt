package com.michaeltchuang.walletsdk.algosdk.di

import com.michaeltchuang.walletsdk.algosdk.domain.usecase.RecoverWithPassphrasePreviewUseCase
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoAccountSdkImpl
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39Sdk
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39SdkImpl
import org.koin.dsl.module

val algoSdkModule = module {
    single { AlgoAccountSdkImpl() }
    single<AlgoKitBip39Sdk> { AlgoKitBip39SdkImpl() }
    factory { RecoverWithPassphrasePreviewUseCase(get()) }
}

