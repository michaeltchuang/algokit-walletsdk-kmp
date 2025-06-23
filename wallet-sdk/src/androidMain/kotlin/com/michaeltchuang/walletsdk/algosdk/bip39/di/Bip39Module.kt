package com.michaeltchuang.walletsdk.algosdk.bip39.di

import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.AlgorandBip39WalletProvider
import org.koin.dsl.module

val bip39Module =
    module {
        single { AlgorandBip39WalletProvider() }
    }
