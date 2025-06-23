package com.michaeltchuang.walletsdk.foundation.security

internal interface SecurityProvidersFactory {
    fun getProviders(): List<SecurityProvider>
}
