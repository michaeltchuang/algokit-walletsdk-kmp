package com.michaeltchuang.walletsdk.core.deeplink.model

import kotlinx.serialization.Serializable

@Serializable
internal data class PeraUri(
    val scheme: String?,
    val host: String?,
    val path: String?,
    val queryParams: Map<String, String?>,
    val fragment: String?,
    val rawUri: String,
) {
    fun isAppLink(): Boolean = host?.startsWith(PERAWALLET_APPLINK_AUTH_KEY) ?: false

    fun getQueryParam(key: String): String? = queryParams[key]

    private companion object {
        const val PERAWALLET_APPLINK_AUTH_KEY = "perawallet.app"
    }
}
