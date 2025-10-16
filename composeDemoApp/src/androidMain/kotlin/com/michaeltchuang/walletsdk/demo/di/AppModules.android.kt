package com.michaeltchuang.walletsdk.demo.di

import com.michaeltchuang.walletsdk.demo.AndroidApp
import com.michaeltchuang.walletsdk.core.algosdk.di.algoSdkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.koinConfiguration

actual fun nativeConfig() =
    koinConfiguration {
        androidLogger()
        androidContext(AndroidApp.instance)
        modules(algoSdkModule)
    }
