package com.michaeltchuang.walletsdk.foundation.di

import com.michaeltchuang.walletsdk.foundation.commonModule
import com.michaeltchuang.walletsdk.foundation.delegateModule
import com.michaeltchuang.walletsdk.foundation.json.jsonModule
import com.michaeltchuang.walletsdk.network.di.networkModule

val foundationModules =
    listOf(
        delegateModule,
        commonModule,
        platformKoinModule(),
        jsonModule,
        networkModule,
    )
