package com.michaeltchuang.walletsdk.core.deeplink.parser

import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLink

interface CreateDeepLink {
    operator fun invoke(url: String): DeepLink
}
