package com.michaeltchuang.walletsdk.core.deeplink.parser

import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLinkPayload

internal interface ParseDeepLinkPayload {
    operator fun invoke(url: String): DeepLinkPayload
}
