package com.michaeltchuang.walletsdk.deeplink.parser.query

/*import com.google.gson.annotations.SerializedName
import com.michaeltchuang.walletsdk.deeplink.model.PeraUri
import com.michaeltchuang.walletsdk.deeplink.model.WebImportQrCode
import com.michaeltchuang.walletsdk.foundation.json.JsonSerializer

internal class WebImportQrCodeQueryParser(
    private val jsonSerializer: JsonSerializer
) : DeepLinkQueryParser<WebImportQrCode?> {

    override fun parseQuery(peraUri: PeraUri): WebImportQrCode? {
        return try {
            getWebImportQrCode(peraUri)
        } catch (e: Exception) {
            null
        }
    }

    private fun getWebImportQrCode(peraUri: PeraUri): WebImportQrCode? {
        val qrCodePayload =
            jsonSerializer.fromJson(peraUri.rawUri, WebQrCode::class.java) ?: return null
        return if (isRecognized(qrCodePayload) && qrCodePayload.action == ACTION_IMPORT_KEY) {
            WebImportQrCode(qrCodePayload.backupId, qrCodePayload.encryptionKey)
        } else {
            null
        }
    }

    private fun isRecognized(webQrCode: WebQrCode): Boolean {
        val intVersion = webQrCode.version.toIntOrNull() ?: return false
        return intVersion <= CURRENT_QR_CODE_VERSION
    }

    internal data class WebQrCode(
        @SerializedName("version") val version: String,
        @SerializedName("action") val action: String,
        @SerializedName("platform") val platform: String?,
        @SerializedName("backupId") val backupId: String,
        @SerializedName("modificationKey") val modificationKey: String?,
        @SerializedName("encryptionKey") val encryptionKey: String,
    )

    private companion object {
        const val CURRENT_QR_CODE_VERSION = 1
        const val ACTION_IMPORT_KEY = "import"
    }
}*/
