package com.michaeltchuang.walletsdk.demo.di

import org.koin.dsl.koinConfiguration

actual fun nativeConfig() =
    koinConfiguration {
        printLogger()
    }
