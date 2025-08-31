package com.michaeltchuang.walletsdk.deeplink.parser.query

import com.michaeltchuang.walletsdk.deeplink.model.PeraUri
import com.michaeltchuang.walletsdk.deeplink.utils.AssetConstants.ALGO_ID
import com.michaeltchuang.walletsdk.deeplink.utils.isCoinbaseDeepLink

internal class AssetIdQueryParser : DeepLinkQueryParser<Long?> {

    override fun parseQuery(peraUri: PeraUri): Long? {
        val assetIdAsString = when {
            isCoinbaseDeepLink(peraUri) -> getAssetIdForCoinbase(peraUri)
            else -> peraUri.getQueryParam(ASSET_ID_QUERY_KEY)
        }
        return assetIdAsString?.toLongOrNull()
    }

    private fun getAssetIdForCoinbase(peraUri: PeraUri): String {
        // algo:31566704/transfer?address=KG2HXWIOQSBOBGJEXSIBNEVNTRD4G4EFIJGRKBG2ZOT7NQ
        val regexWithAssetId = COINBASE_ASSET_ID_REGEX.toRegex()
        val matchResultWithAssetId = regexWithAssetId.find(peraUri.rawUri)
        return matchResultWithAssetId?.destructured?.component1() ?: ALGO_ID.toString()
    }

    private companion object {
        const val ASSET_ID_QUERY_KEY = "asset"
        private const val COINBASE_ASSET_ID_REGEX = """algo:(\d+)"""
    }
}
