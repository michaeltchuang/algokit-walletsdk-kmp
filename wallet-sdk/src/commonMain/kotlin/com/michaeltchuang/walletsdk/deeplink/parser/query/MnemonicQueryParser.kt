package com.michaeltchuang.walletsdk.deeplink.parser.query

import com.michaeltchuang.walletsdk.deeplink.model.PeraUri
import com.michaeltchuang.walletsdk.foundation.json.JsonSerializer
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

internal class MnemonicQueryParser(
    private val jsonSerializer: JsonSerializer,
) : DeepLinkQueryParser<String?> {
    override fun parseQuery(peraUri: PeraUri): String? =
        try {
            val payload: MnemonicPayload = (jsonSerializer.fromJson(peraUri.rawUri, typeOf<MnemonicPayload>())!!)
            payload.mnemonic
        } catch (e: Exception) {
            null
        }

    @Serializable
    internal data class MnemonicPayload(
        val version: Double? = null,
        val mnemonic: String,
    )
}
