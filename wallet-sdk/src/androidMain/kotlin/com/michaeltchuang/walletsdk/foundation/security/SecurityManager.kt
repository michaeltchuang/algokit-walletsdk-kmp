package com.michaeltchuang.walletsdk.foundation.security

internal interface SecurityManager {
    fun registerProvider(securityProvider: SecurityProvider)
}
