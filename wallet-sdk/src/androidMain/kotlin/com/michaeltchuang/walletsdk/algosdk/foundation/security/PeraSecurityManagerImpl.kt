 

package com.michaeltchuang.walletsdk.algosdk.foundation.security

import javax.inject.Inject

internal class PeraSecurityManagerImpl @Inject constructor(
    private val securityManager: SecurityManager,
    private val securityProvidersFactory: SecurityProvidersFactory
) : PeraSecurityManager {

    override fun initializeSecurityManager() {
        val securityProviders = securityProvidersFactory.getProviders()
        securityProviders.forEach { provider ->
            securityManager.registerProvider(provider)
        }
    }
}
