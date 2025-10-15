package com.michaeltchuang.walletsdk.core.foundation.di

import com.michaeltchuang.walletsdk.core.foundation.commonModule
import com.michaeltchuang.walletsdk.core.foundation.delegateModule
import com.michaeltchuang.walletsdk.core.foundation.json.jsonModule
import com.michaeltchuang.walletsdk.core.network.di.networkModule

val foundationModules =
    listOf(
        delegateModule,
        commonModule,
        platformKoinModule(),
        jsonModule,
        networkModule,
    )
