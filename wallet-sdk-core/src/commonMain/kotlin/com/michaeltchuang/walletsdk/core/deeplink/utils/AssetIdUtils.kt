package com.michaeltchuang.walletsdk.core.deeplink.utils

import com.michaeltchuang.walletsdk.core.deeplink.utils.AssetConstants.ALGO_ID

// Backend accepts ALGO with asset id 0. Remove this line if they accepts to change ALGO ID as -7
fun getSafeAssetIdForRequest(assetId: Long): Long = if (assetId == ALGO_ID) 0 else assetId

// Backend returns ALGO with asset id 0. Remove this line if they accepts to change ALGO ID as -7
fun getSafeAssetIdForResponse(assetId: Long?): Long? = if (assetId == 0L) ALGO_ID else assetId
