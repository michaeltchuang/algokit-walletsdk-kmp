

package com.michaeltchuang.walletsdk.algosdk.transaction.sdk.mapper

import android.util.Base64
import com.algorand.algosdk.sdk.SuggestedParams
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.SuggestedTransactionParams
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.SuggestedTransactionParams.TransactionFee.FeeType
import com.michaeltchuang.walletsdk.encryption.domain.manager.Base64Manager

internal class SuggestedParamsMapperImpl(
    private val base64Manager: Base64Manager,
) : SuggestedParamsMapper {
    override fun invoke(
        params: SuggestedTransactionParams,
        addGenesis: Boolean,
    ): SuggestedParams =
        SuggestedParams().apply {
            genesisID = if (addGenesis) params.genesisId else ""
            firstRoundValid = params.lastRound
            lastRoundValid = params.lastRound + ROUND_THRESHOLD
            genesisHash = base64Manager.decode(params.genesisHash, Base64.DEFAULT)
            fee = params.fee.fee
            flatFee = params.fee.type == FeeType.Flat
        }

    companion object {
        private const val ROUND_THRESHOLD = 1000
    }
}
