package com.michaeltchuang.walletsdk.foundation.security

import javax.inject.Inject
import org.bouncycastle.jce.provider.BouncyCastleProvider

internal class SecurityProvidersFactoryImpl @Inject constructor() : SecurityProvidersFactory {

    override fun getProviders(): List<SecurityProvider> {
        return listOf(
            getBouncyCastleProvider()
        )
    }

    private fun getBouncyCastleProvider(): SecurityProvider {
        return SecurityProvider(
            provider = BouncyCastleProvider(),
            priority = 0
        )
    }
}
