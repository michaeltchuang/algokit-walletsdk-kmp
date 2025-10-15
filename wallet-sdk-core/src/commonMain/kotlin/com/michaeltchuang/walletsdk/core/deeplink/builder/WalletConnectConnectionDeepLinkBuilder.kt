package com.michaeltchuang.walletsdk.core.deeplink.builder

import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLinkPayload

internal class WalletConnectConnectionDeepLinkBuilder : DeepLinkBuilder {
    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean =
        with(payload) {
            walletConnectUrl != null &&
                accountAddress == null &&
                assetId == null &&
                amount == null &&
                note == null &&
                url == null &&
                xnote == null &&
                label == null &&
                webImportQrCode == null &&
                notificationGroupType == null
        }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink =
        DeepLink.WalletConnectConnection(uri = payload.walletConnectUrl.orEmpty())
}
