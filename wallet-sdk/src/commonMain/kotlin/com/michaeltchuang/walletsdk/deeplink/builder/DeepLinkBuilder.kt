package com.michaeltchuang.walletsdk.deeplink.builder

import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.deeplink.model.DeepLinkPayload

internal interface DeepLinkBuilder {
    fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean

    fun createDeepLink(payload: DeepLinkPayload): DeepLink
}
