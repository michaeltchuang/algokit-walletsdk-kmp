package com.michaeltchuang.walletsdk.core.deeplink.parser.query

import com.michaeltchuang.walletsdk.core.deeplink.model.PeraUri

internal interface DeepLinkQueryParser<T> {
    fun parseQuery(peraUri: PeraUri): T
}
