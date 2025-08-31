package com.michaeltchuang.walletsdk.deeplink.builder

import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.deeplink.model.DeepLinkPayload

internal class DiscoverBrowserDeepLinkBuilder : DeepLinkBuilder {

    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean {
        return with(payload) {
            url != null &&
                    accountAddress == null &&
                    assetId == null &&
                    amount == null &&
                    walletConnectUrl == null &&
                    note == null &&
                    xnote == null &&
                    label == null &&
                    webImportQrCode == null &&
                    notificationGroupType == null &&
                    mnemonic == null
        }
    }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink {
        return DeepLink.DiscoverBrowser(webUrl = payload.url.orEmpty())
    }
}
