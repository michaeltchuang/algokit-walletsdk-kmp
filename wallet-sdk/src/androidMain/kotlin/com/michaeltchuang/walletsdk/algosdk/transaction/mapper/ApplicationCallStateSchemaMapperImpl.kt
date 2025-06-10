 

package com.michaeltchuang.walletsdk.algosdk.transaction.mapper

import com.michaeltchuang.walletsdk.algosdk.transaction.model.ApplicationCallStateSchema
import com.michaeltchuang.walletsdk.algosdk.transaction.model.payload.RawTransactionApplicationCallStateSchemaPayload
import javax.inject.Inject

internal class ApplicationCallStateSchemaMapperImpl @Inject constructor() :
    ApplicationCallStateSchemaMapper {

    override fun invoke(payload: RawTransactionApplicationCallStateSchemaPayload?): ApplicationCallStateSchema {
        return ApplicationCallStateSchema(
            numberOfBytes = payload?.numberOfBytes,
            numberOfInts = payload?.numberOfInts
        )
    }
}
