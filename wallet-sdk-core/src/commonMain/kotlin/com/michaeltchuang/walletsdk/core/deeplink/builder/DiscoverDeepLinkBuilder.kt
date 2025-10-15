package com.michaeltchuang.walletsdk.core.deeplink.builder

import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLinkPayload

internal class DiscoverDeepLinkBuilder : DeepLinkBuilder {
    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean =
        with(payload) {
            host == DISCOVER_HOST_NAME && path != null
        }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink = DeepLink.Discover(path = payload.path.orEmpty())

    companion object {
        private const val DISCOVER_HOST_NAME = "discover"
    }
}
