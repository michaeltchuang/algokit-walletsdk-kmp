package com.michaeltchuang.walletsdk.foundation.security

import org.bouncycastle.jce.provider.BouncyCastleProvider
import javax.inject.Inject

internal class SecurityProvidersFactoryImpl
    @Inject
    constructor() : SecurityProvidersFactory {
        override fun getProviders(): List<SecurityProvider> =
            listOf(
                getBouncyCastleProvider(),
            )

        private fun getBouncyCastleProvider(): SecurityProvider =
            SecurityProvider(
                provider = BouncyCastleProvider(),
                priority = 0,
            )
    }
