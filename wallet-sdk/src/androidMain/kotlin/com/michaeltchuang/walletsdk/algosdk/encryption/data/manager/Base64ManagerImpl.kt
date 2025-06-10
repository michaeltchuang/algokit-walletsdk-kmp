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

package com.michaeltchuang.walletsdk.algosdk.encryption.data.manager

import com.michaeltchuang.walletsdk.algosdk.encryption.domain.manager.Base64Manager
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

internal class Base64ManagerImpl @Inject constructor() : Base64Manager {

    @OptIn(ExperimentalEncodingApi::class)
    override fun encode(byteArray: ByteArray): String {
        return Base64.encode(byteArray)
    }

    @OptIn(ExperimentalEncodingApi::class)
    override fun decode(value: String): ByteArray {
        return Base64.decode(value)
    }

    @OptIn(ExperimentalEncodingApi::class)
    override fun decode(value: String, flags: Int): ByteArray {
        return Base64.decode(value, flags)
    }
}
