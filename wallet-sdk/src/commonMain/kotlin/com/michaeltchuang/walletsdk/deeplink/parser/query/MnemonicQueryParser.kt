package com.michaeltchuang.walletsdk.deeplink.parser.query

import com.michaeltchuang.walletsdk.deeplink.model.PeraUri

internal class MnemonicQueryParser(
    /* private val jsonSerializer: JsonSerializer*/
) : DeepLinkQueryParser<String?> {

    override fun parseQuery(peraUri: PeraUri): String? {
    /*    return try {
            jsonSerializer.fromJson(peraUri.rawUri, MnemonicPayload::class)?.mnemonic
        } catch (e: Exception) {
            null
        }*/
        return null
    }

    internal data class MnemonicPayload(
        val version: Double? = null,
        val mnemonic: String
    )
}
