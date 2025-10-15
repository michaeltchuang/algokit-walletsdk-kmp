package com.michaeltchuang.walletsdk.core.deeplink.builder

import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLinkPayload

internal class CardsDeepLinkBuilder : DeepLinkBuilder {
    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean =
        with(payload) {
            host == CARDS_HOST_NAME && path != null
        }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink = DeepLink.Cards(path = payload.path.orEmpty())

    companion object {
        private const val CARDS_HOST_NAME = "cards"
    }
}
