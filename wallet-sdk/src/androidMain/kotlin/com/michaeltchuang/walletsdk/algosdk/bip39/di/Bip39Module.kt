package com.michaeltchuang.walletsdk.algosdk.bip39.di

import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.AlgorandBip39WalletProvider
import org.koin.dsl.module

/*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object Bip39Module {

    @Provides
    fun provideBip39ApiProvider(impl: AlgorandBip39WalletProvider): Bip39WalletProvider = impl
}
*/

val bip39Module = module {
    single { AlgorandBip39WalletProvider() }
}