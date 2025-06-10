/*
 * Copyright 2022-2025 Pera Wallet, LDA
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.michaeltchuang.walletsdk.algosdk.transaction.mapper

import com.michaeltchuang.walletsdk.algosdk.transaction.model.ApplicationCallStateSchema
import com.michaeltchuang.walletsdk.algosdk.transaction.model.AssetConfigParameters
import com.michaeltchuang.walletsdk.algosdk.transaction.model.RawTransaction
import com.michaeltchuang.walletsdk.algosdk.transaction.model.RawTransactionType
import com.michaeltchuang.walletsdk.algosdk.transaction.model.payload.RawTransactionApplicationCallStateSchemaPayload
import com.michaeltchuang.walletsdk.algosdk.transaction.model.payload.RawTransactionAssetConfigParametersPayload
import com.michaeltchuang.walletsdk.algosdk.transaction.model.payload.RawTransactionPayload
import com.michaeltchuang.walletsdk.algosdk.transaction.model.payload.RawTransactionTypePayload

internal interface RawTransactionMapper {
    operator fun invoke(payload: RawTransactionPayload): RawTransaction
}

internal interface RawTransactionTypeMapper {
    operator fun invoke(payload: RawTransactionTypePayload): RawTransactionType
}

internal interface AssetConfigParametersMapper {
    operator fun invoke(payload: RawTransactionAssetConfigParametersPayload?): AssetConfigParameters
}

internal interface ApplicationCallStateSchemaMapper {
    operator fun invoke(payload: RawTransactionApplicationCallStateSchemaPayload?): ApplicationCallStateSchema
}
