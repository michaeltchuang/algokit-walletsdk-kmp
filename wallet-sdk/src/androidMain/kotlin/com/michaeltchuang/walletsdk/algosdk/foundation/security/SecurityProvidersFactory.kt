 

package com.michaeltchuang.walletsdk.algosdk.foundation.security

internal interface SecurityProvidersFactory {
    fun getProviders(): List<SecurityProvider>
}
