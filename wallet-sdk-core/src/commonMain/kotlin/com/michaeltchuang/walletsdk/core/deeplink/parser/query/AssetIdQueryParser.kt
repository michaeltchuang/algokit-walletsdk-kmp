package com.michaeltchuang.walletsdk.core.deeplink.parser.query

import com.michaeltchuang.walletsdk.core.deeplink.model.AlgorandUri
import com.michaeltchuang.walletsdk.core.deeplink.utils.AssetConstants.ALGO_ID
import com.michaeltchuang.walletsdk.core.deeplink.utils.isCoinbaseDeepLink

internal class AssetIdQueryParser : DeepLinkQueryParser<Long?> {
    override fun parseQuery(algorandUri: AlgorandUri): Long? {
        val assetIdAsString =
            when {
                isCoinbaseDeepLink(algorandUri) -> getAssetIdForCoinbase(algorandUri)
                else -> algorandUri.getQueryParam(ASSET_ID_QUERY_KEY)
            }
        return assetIdAsString?.toLongOrNull()
    }

    private fun getAssetIdForCoinbase(algorandUri: AlgorandUri): String {
        // algo:31566704/transfer?address=KG2HXWIOQSBOBGJEXSIBNEVNTRD4G4EFIJGRKBG2ZOT7NQ
        val regexWithAssetId = COINBASE_ASSET_ID_REGEX.toRegex()
        val matchResultWithAssetId = regexWithAssetId.find(algorandUri.rawUri)
        return matchResultWithAssetId?.destructured?.component1() ?: ALGO_ID.toString()
    }

    private companion object {
        const val ASSET_ID_QUERY_KEY = "asset"
        private const val COINBASE_ASSET_ID_REGEX = """algo:(\d+)"""
    }
}
