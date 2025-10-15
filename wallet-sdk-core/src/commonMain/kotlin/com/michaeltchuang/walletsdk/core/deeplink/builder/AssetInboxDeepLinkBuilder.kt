package com.michaeltchuang.walletsdk.core.deeplink.builder

import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLinkPayload
import com.michaeltchuang.walletsdk.core.deeplink.model.NotificationGroupType

internal class AssetInboxDeepLinkBuilder : DeepLinkBuilder {
    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean =
        with(payload) {
            accountAddress != null && notificationGroupType == NotificationGroupType.ASSET_INBOX
        }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink =
        DeepLink.AssetInbox(
            address = payload.accountAddress.orEmpty(),
            notificationGroupType = payload.notificationGroupType ?: NotificationGroupType.DEFAULT,
        )
}
