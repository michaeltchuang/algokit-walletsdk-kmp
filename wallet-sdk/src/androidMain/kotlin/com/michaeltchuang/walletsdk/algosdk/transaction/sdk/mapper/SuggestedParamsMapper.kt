

package com.michaeltchuang.walletsdk.algosdk.transaction.sdk.mapper

import com.algorand.algosdk.sdk.SuggestedParams
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.SuggestedTransactionParams

internal interface SuggestedParamsMapper {
    operator fun invoke(
        params: SuggestedTransactionParams,
        addGenesis: Boolean,
    ): SuggestedParams
}
