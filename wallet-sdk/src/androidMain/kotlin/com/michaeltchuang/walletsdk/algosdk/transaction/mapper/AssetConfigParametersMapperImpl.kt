

package com.michaeltchuang.walletsdk.algosdk.transaction.mapper

import com.michaeltchuang.walletsdk.algosdk.transaction.model.AssetConfigParameters
import com.michaeltchuang.walletsdk.algosdk.transaction.model.payload.RawTransactionAssetConfigParametersPayload

internal class AssetConfigParametersMapperImpl : AssetConfigParametersMapper {
    override fun invoke(payload: RawTransactionAssetConfigParametersPayload?): AssetConfigParameters {
        return AssetConfigParameters(
            totalSupply = payload?.totalSupply,
            decimal = payload?.decimal,
            isFrozen = payload?.isFrozen,
            unitName = payload?.unitName,
            name = payload?.name,
            url = payload?.url,
            metadataHash = payload?.metadataHash,
            managerAddress = payload?.managerAddress,
            reserveAddress = payload?.reserveAddress,
            frozenAddress = payload?.frozenAddress,
            clawbackAddress = payload?.clawbackAddress,
        )
    }
}
