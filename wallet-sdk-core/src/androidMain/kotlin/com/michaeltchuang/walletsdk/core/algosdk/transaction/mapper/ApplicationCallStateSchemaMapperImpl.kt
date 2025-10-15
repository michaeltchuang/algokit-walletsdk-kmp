package com.michaeltchuang.walletsdk.core.algosdk.transaction.mapper

import com.michaeltchuang.walletsdk.core.algosdk.transaction.model.ApplicationCallStateSchema
import com.michaeltchuang.walletsdk.core.algosdk.transaction.model.payload.RawTransactionApplicationCallStateSchemaPayload
import javax.inject.Inject

internal class ApplicationCallStateSchemaMapperImpl
    @Inject
    constructor() : ApplicationCallStateSchemaMapper {
        override fun invoke(payload: RawTransactionApplicationCallStateSchemaPayload?): ApplicationCallStateSchema =
            ApplicationCallStateSchema(
                numberOfBytes = payload?.numberOfBytes,
                numberOfInts = payload?.numberOfInts,
            )
    }
