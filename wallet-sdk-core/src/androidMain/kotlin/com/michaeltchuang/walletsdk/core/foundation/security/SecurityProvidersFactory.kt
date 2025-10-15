package com.michaeltchuang.walletsdk.core.foundation.security

internal interface SecurityProvidersFactory {
    fun getProviders(): List<SecurityProvider>
}
