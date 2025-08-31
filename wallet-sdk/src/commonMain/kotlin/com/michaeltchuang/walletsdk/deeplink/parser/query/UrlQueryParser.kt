package com.michaeltchuang.walletsdk.deeplink.parser.query

import com.michaeltchuang.walletsdk.deeplink.model.PeraUri

/*import com.michaeltchuang.walletsdk.encryption.domain.manager.Base64Manager

internal class UrlQueryParser(
    private val base64Manager: Base64Manager
) : DeepLinkQueryParser<String?> {

    override fun parseQuery(peraUri: PeraUri): String? {
        val urlQuery = peraUri.getQueryParam(URL_QUERY_KEY) ?: return null
        return try {
            base64Manager.decode(urlQuery).takeIf { it.isNotEmpty() }?.decodeToString()
        } catch (exception: Exception) {
            null
        }
    }

    private companion object {
        const val URL_QUERY_KEY = "url"
    }
}*/
