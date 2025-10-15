package com.michaeltchuang.walletsdk.core.deeplink.builder

import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLinkPayload

internal interface DeepLinkBuilder {
    fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean

    fun createDeepLink(payload: DeepLinkPayload): DeepLink
}
