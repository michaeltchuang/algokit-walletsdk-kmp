package com.michaeltchuang.walletsdk.core.deeplink.parser.query

import com.michaeltchuang.walletsdk.core.deeplink.model.AlgorandUri

internal interface DeepLinkQueryParser<T> {
    fun parseQuery(algorandUri: AlgorandUri): T
}
