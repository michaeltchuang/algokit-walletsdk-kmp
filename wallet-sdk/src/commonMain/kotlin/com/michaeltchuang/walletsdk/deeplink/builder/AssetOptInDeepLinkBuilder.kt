package com.michaeltchuang.walletsdk.deeplink.builder

import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.deeplink.model.DeepLinkPayload

internal class AssetOptInDeepLinkBuilder : DeepLinkBuilder {
    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean =
        with(payload) {
            val doesDeeplinkHaveAssetOptInQueries = assetId != null && amount == "0"
            doesDeeplinkHaveAssetOptInQueries &&
                accountAddress == null &&
                walletConnectUrl == null &&
                note == null &&
                xnote == null &&
                url == null &&
                label == null &&
                webImportQrCode == null &&
                notificationGroupType == null
        }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink =
        payload.assetId?.let { safeAssetId ->
            DeepLink.AssetOptIn(safeAssetId)
        } ?: DeepLink.Undefined(payload.rawDeepLinkUri)
}
