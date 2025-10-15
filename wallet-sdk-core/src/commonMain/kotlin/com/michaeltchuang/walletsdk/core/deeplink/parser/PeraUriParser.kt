package com.michaeltchuang.walletsdk.core.deeplink.parser

import com.michaeltchuang.walletsdk.core.deeplink.model.PeraUri

internal interface PeraUriParser {
    fun parseUri(uri: String): PeraUri
}
