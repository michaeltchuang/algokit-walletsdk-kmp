

package com.michaeltchuang.walletsdk.algosdk.transaction.mapper

import com.michaeltchuang.walletsdk.algosdk.transaction.model.RawTransactionType
import com.michaeltchuang.walletsdk.algosdk.transaction.model.payload.RawTransactionTypePayload

internal class RawTransactionTypeMapperImpl : RawTransactionTypeMapper {
    override fun invoke(payload: RawTransactionTypePayload): RawTransactionType =
        when (payload) {
            RawTransactionTypePayload.PAY_TRANSACTION -> RawTransactionType.PAY_TRANSACTION
            RawTransactionTypePayload.ASSET_TRANSACTION -> RawTransactionType.ASSET_TRANSACTION
            RawTransactionTypePayload.APP_TRANSACTION -> RawTransactionType.APP_TRANSACTION
            RawTransactionTypePayload.ASSET_CONFIGURATION -> RawTransactionType.ASSET_CONFIGURATION
            RawTransactionTypePayload.KEYREG_TRANSACTION -> RawTransactionType.KEYREG_TRANSACTION
            RawTransactionTypePayload.HEARTBEAT_TRANSACTION -> RawTransactionType.HEARTBEAT_TRANSACTION
            RawTransactionTypePayload.UNDEFINED -> RawTransactionType.UNDEFINED
        }
}
