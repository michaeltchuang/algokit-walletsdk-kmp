package com.michaeltchuang.walletsdk.core.deeplink.parser

import com.michaeltchuang.walletsdk.core.deeplink.model.AlgorandUri

internal interface AlgorandUriParser {
    fun parseUri(uri: String): AlgorandUri
}
