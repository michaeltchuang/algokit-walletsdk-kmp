package com.michaeltchuang.walletsdk.core.deeplink.parser.query

import com.michaeltchuang.walletsdk.core.deeplink.model.NotificationGroupType
import com.michaeltchuang.walletsdk.core.deeplink.model.PeraUri

internal class NotificationGroupTypeQueryParser : DeepLinkQueryParser<NotificationGroupType?> {
    override fun parseQuery(peraUri: PeraUri): NotificationGroupType? =
        when (getNotificationGroupQueryOrNull(peraUri)) {
            NOTIFICATION_ACTION_ASSET_TRANSACTIONS -> NotificationGroupType.TRANSACTIONS
            NOTIFICATION_ACTION_ASSET_OPTIN -> NotificationGroupType.OPT_IN
            NOTIFICATION_ASSET_INBOX -> NotificationGroupType.ASSET_INBOX
            else -> null
        }

    private fun getNotificationGroupQueryOrNull(peraUri: PeraUri): String {
        val builder = StringBuilder(peraUri.host.orEmpty())
        if (!peraUri.path.isNullOrBlank()) {
            builder.append("/").append(peraUri.path)
        }
        return builder.toString()
    }

    private companion object {
        const val NOTIFICATION_ACTION_ASSET_TRANSACTIONS = "asset/transactions"
        const val NOTIFICATION_ACTION_ASSET_OPTIN = "asset/opt-in"
        const val NOTIFICATION_ASSET_INBOX = "asset-inbox"
    }
}
