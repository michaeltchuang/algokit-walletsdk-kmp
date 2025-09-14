package com.michaeltchuang.walletsdk.foundation.json

import org.koin.dsl.module

val jsonModule = module {
    single<JsonSerializer> { JsonSerializerImpl() }
}

