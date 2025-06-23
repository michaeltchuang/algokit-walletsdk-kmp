package com.michaeltchuang.walletsdk.foundation.security

import java.security.Provider

data class SecurityProvider(
    val provider: Provider,
    val priority: Int,
)
