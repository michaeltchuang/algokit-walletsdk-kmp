package com.michaeltchuang.walletsdk.deeplink.builder

import com.michaeltchuang.walletsdk.deeplink.builder.DeepLinkBuilder
import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.deeplink.model.DeepLinkPayload

internal class AccountAddressDeepLinkBuilder : DeepLinkBuilder {

    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean {
        return with(payload) {
            accountAddress != null &&
                    walletConnectUrl == null &&
                    assetId == null &&
                    amount == null &&
                    note == null &&
                    xnote == null &&
                    url == null &&
                    webImportQrCode == null &&
                    notificationGroupType == null &&
                    type != "keyreg"
        }
    }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink {
        return DeepLink.AccountAddress(
            address = payload.accountAddress.orEmpty(),
            label = payload.label
        )
    }
}
