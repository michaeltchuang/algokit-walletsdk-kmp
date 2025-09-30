package com.michaeltchuang.walletsdk.deeplink.builder

import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.deeplink.model.DeepLinkPayload

internal class StakingDeepLinkBuilder : DeepLinkBuilder {
    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean =
        with(payload) {
            host == STAKING_HOST_NAME && path != null
        }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink = DeepLink.Staking(path = payload.path.orEmpty())

    companion object {
        private const val STAKING_HOST_NAME = "staking"
    }
}
