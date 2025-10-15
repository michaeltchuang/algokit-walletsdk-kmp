package com.michaeltchuang.walletsdk.core.deeplink.builder

import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLinkPayload

internal class WebImportQrCodeDeepLinkBuilder : DeepLinkBuilder {
    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean =
        with(payload) {
            webImportQrCode != null &&
                accountAddress == null &&
                assetId == null &&
                amount == null &&
                walletConnectUrl == null &&
                url == null &&
                note == null &&
                xnote == null &&
                label == null &&
                notificationGroupType == null &&
                mnemonic == null
        }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink =
        DeepLink.WebImportQrCode(
            backupId = payload.webImportQrCode?.backupId.orEmpty(),
            encryptionKey = payload.webImportQrCode?.encryptionKey.orEmpty(),
        )
}
