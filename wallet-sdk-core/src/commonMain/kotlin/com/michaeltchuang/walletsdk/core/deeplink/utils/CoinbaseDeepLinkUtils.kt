package com.michaeltchuang.walletsdk.core.deeplink.utils

import com.michaeltchuang.walletsdk.core.deeplink.model.AlgorandUri

private const val COINBASE_DEEPLINK_ROOT = "algo:"

internal fun isCoinbaseDeepLink(uri: AlgorandUri): Boolean = uri.rawUri.startsWith(COINBASE_DEEPLINK_ROOT, ignoreCase = true)
