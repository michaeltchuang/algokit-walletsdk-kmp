package com.michaeltchuang.walletsdk.deeplink.parser.query

import com.michaeltchuang.walletsdk.deeplink.model.PeraUri

internal class WalletConnectUrlQueryParser : DeepLinkQueryParser<String?> {
    override fun parseQuery(peraUri: PeraUri): String? =
        with(peraUri) {
            val parsedUrl =
                if (isAppLink()) getUriFromAppLink(rawUri) else getUriFromDeepLink(rawUri)
            parsedUrl.takeIf { it?.startsWith(WALLET_CONNECT_AUTH_KEY) == true }
        }

    private fun getUriFromAppLink(rawUri: String): String? = rawUri.split(PERAWALLET_WC_AUTH_KEY).lastOrNull()?.removePrefix("/")

    private fun getUriFromDeepLink(rawUri: String): String? = rawUri.split("://").lastOrNull()

    private companion object {
        const val PERAWALLET_WC_AUTH_KEY = "perawallet-wc"
        const val WALLET_CONNECT_AUTH_KEY = "wc:"
    }
}
