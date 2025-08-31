package com.michaeltchuang.walletsdk.deeplink.parser

import com.michaeltchuang.walletsdk.deeplink.model.DeepLinkPayload

internal interface ParseDeepLinkPayload {

    operator fun invoke(url: String): DeepLinkPayload
}
