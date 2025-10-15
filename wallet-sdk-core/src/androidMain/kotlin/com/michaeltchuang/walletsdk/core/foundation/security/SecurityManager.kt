package com.michaeltchuang.walletsdk.core.foundation.security

internal interface SecurityManager {
    fun registerProvider(securityProvider: SecurityProvider)
}
