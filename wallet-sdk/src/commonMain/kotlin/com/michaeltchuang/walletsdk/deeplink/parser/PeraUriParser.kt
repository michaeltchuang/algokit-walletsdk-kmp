package com.michaeltchuang.walletsdk.deeplink.parser

import com.michaeltchuang.walletsdk.deeplink.model.PeraUri

internal interface PeraUriParser {
    fun parseUri(uri: String): PeraUri
}
