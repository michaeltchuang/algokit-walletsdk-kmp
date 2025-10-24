package com.michaeltchuang.walletsdk.core.deeplink.parser.query

import com.michaeltchuang.walletsdk.core.deeplink.model.AlgorandUri
import com.michaeltchuang.walletsdk.core.foundation.json.JsonSerializer
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

internal class MnemonicQueryParser(
    private val jsonSerializer: JsonSerializer,
) : DeepLinkQueryParser<String?> {
    override fun parseQuery(algorandUri: AlgorandUri): String? =
        try {
            val payload: MnemonicPayload = (jsonSerializer.fromJson(algorandUri.rawUri, typeOf<MnemonicPayload>())!!)
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
