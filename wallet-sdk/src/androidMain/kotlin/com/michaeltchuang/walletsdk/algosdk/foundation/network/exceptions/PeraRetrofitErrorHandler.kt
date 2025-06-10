 

package com.michaeltchuang.walletsdk.algosdk.foundation.network.exceptions

import com.algorand.algosdk.v2.client.common.Response

interface PeraRetrofitErrorHandler {

    fun <T> parse(response: Response<T>): ParsedError
}
