package com.michaeltchuang.walletsdk.deeplink.parser

import com.michaeltchuang.walletsdk.deeplink.builder.DeepLinkBuilder
import com.michaeltchuang.walletsdk.deeplink.model.DeepLink

internal class CreateDeepLinkImpl(
    private val parseDeepLinkPayload: ParseDeepLinkPayload,
    private val mnemonicDeepLinkBuilder: DeepLinkBuilder,
    private val keyRegTransactionDeepLinkBuilder: DeepLinkBuilder,
) : CreateDeepLink {
    override fun invoke(url: String): DeepLink {
        val payload = parseDeepLinkPayload(url)

        return when {
            mnemonicDeepLinkBuilder.doesDeeplinkMeetTheRequirements(payload) -> {
                mnemonicDeepLinkBuilder.createDeepLink(payload)
            }
            keyRegTransactionDeepLinkBuilder.doesDeeplinkMeetTheRequirements(payload) -> {
                keyRegTransactionDeepLinkBuilder.createDeepLink(payload)
            }
            else -> DeepLink.Undefined(url)
        }
    }
}
