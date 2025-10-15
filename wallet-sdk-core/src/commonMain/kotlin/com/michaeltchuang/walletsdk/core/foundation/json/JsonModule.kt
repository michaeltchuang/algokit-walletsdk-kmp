package com.michaeltchuang.walletsdk.core.foundation.json

import org.koin.dsl.module

val jsonModule =
    module {
        single<JsonSerializer> { JsonSerializerImpl() }
    }
