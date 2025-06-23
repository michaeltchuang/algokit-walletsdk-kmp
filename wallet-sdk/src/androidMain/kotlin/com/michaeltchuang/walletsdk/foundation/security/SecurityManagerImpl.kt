package com.michaeltchuang.walletsdk.foundation.security

import java.security.Security
import javax.inject.Inject

internal class SecurityManagerImpl @Inject constructor() : SecurityManager {

    override fun registerProvider(securityProvider: SecurityProvider) {
        Security.removeProvider(securityProvider.provider.name)
        Security.insertProviderAt(securityProvider.provider, securityProvider.priority)
    }
}
