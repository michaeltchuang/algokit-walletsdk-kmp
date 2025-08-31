package com.michaeltchuang.walletsdk.deeplink.builder

import com.michaeltchuang.walletsdk.deeplink.builder.DeepLinkBuilder
import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.deeplink.model.DeepLinkPayload
import com.michaeltchuang.walletsdk.deeplink.model.NotificationGroupType

internal class AssetInboxDeepLinkBuilder : DeepLinkBuilder {

    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean {
        return with(payload) {
            accountAddress != null && notificationGroupType == NotificationGroupType.ASSET_INBOX
        }
    }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink {
        return DeepLink.AssetInbox(
            address = payload.accountAddress.orEmpty(),
            notificationGroupType = payload.notificationGroupType ?: NotificationGroupType.DEFAULT
        )
    }
}
