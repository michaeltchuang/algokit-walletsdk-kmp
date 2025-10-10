package com.michaeltchuang.walletsdk.transaction.data

/*internal class BuildKeyRegOnlineTransactionImpl  :
    BuildKeyRegOnlineTransaction {

    override fun invoke(
        params: OnlineKeyRegTransactionPayload
    ): ByteArray? {
        return try {
            createTransaction(params)
        } catch (e: Exception) {
            null
        }
    }

    private fun createTransaction(params: OnlineKeyRegTransactionPayload): ByteArray {
        return with(params) {
            val suggestedParams = params.txnParams.toSuggestedParams()

            if (flatFee != null) {
                suggestedParams.fee = flatFee.toLong()
                suggestedParams.flatFee = true
            }

            Sdk.makeKeyRegTxnWithStateProofKey(
                senderAddress,
                note?.toByteArray(),
                suggestedParams,
                voteKey.standardizeBase64(),
                selectionPublicKey.standardizeBase64(),
                stateProofKey.standardizeBase64(),
                voteFirstRound.toBigIntegerOrZero().toUint64(),
                voteLastRound.toBigIntegerOrZero().toUint64(),
                voteKeyDilution.toBigIntegerOrZero().toUint64(),
                false
            )
        }
    }
}*/
