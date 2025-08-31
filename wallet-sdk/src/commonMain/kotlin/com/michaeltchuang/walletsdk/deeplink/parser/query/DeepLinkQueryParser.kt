package com.michaeltchuang.walletsdk.deeplink.parser.query

import com.michaeltchuang.walletsdk.deeplink.model.PeraUri

internal interface DeepLinkQueryParser<T> {
    fun parseQuery(peraUri: PeraUri): T
}
