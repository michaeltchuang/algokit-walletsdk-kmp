package com.michaeltchuang.walletsdk.deeplink.parser

import com.michaeltchuang.walletsdk.deeplink.model.DeepLink

interface CreateDeepLink {
    operator fun invoke(url: String): DeepLink
}
