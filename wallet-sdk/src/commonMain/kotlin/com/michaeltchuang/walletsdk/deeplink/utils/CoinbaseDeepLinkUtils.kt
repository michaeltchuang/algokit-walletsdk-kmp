package com.michaeltchuang.walletsdk.deeplink.utils

import com.michaeltchuang.walletsdk.deeplink.model.PeraUri

private const val COINBASE_DEEPLINK_ROOT = "algo:"

internal fun isCoinbaseDeepLink(uri: PeraUri): Boolean = uri.rawUri.startsWith(COINBASE_DEEPLINK_ROOT, ignoreCase = true)
