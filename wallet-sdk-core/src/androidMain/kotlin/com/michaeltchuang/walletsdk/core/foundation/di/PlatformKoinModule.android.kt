package com.michaeltchuang.walletsdk.core.foundation.di

import com.michaeltchuang.walletsdk.core.foundation.database.AlgoKitDatabase
import com.michaeltchuang.walletsdk.core.foundation.database.getAlgoKitDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformKoinModule(): Module =
    module {
        single<AlgoKitDatabase> { getAlgoKitDatabase(get()) }
    }
