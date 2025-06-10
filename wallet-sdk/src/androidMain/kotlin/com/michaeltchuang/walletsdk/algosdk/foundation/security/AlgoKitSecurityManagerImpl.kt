 

package com.michaeltchuang.walletsdk.algosdk.foundation.security

import javax.inject.Inject

internal class AlgoKitSecurityManagerImpl @Inject constructor(
    private val securityManager: SecurityManager,
    private val securityProvidersFactory: SecurityProvidersFactory
) : AlgoKitSecurityManager {

    override fun initializeSecurityManager() {
        val securityProviders = securityProvidersFactory.getProviders()
        securityProviders.forEach { provider ->
            securityManager.registerProvider(provider)
        }
    }
}
