package com.michaeltchuang.walletsdk.foundation.di

import com.michaeltchuang.walletsdk.foundation.database.AlgoKitDatabase
import com.michaeltchuang.walletsdk.foundation.database.getAlgoKitDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformKoinModule(): Module =
    module {
        single<AlgoKitDatabase> { getAlgoKitDatabase() }
    }
