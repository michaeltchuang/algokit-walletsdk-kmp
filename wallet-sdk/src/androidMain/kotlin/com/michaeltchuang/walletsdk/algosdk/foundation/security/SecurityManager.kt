 

package com.michaeltchuang.walletsdk.algosdk.foundation.security

internal interface SecurityManager {
    fun registerProvider(securityProvider: SecurityProvider)
}
