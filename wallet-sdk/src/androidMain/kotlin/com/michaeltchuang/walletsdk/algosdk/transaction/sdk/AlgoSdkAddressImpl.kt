 

package com.michaeltchuang.walletsdk.algosdk.transaction.sdk

import android.util.Base64
import com.algorand.algosdk.sdk.Sdk
import com.michaeltchuang.walletsdk.encryption.domain.manager.Base64Manager
import com.michaeltchuang.walletsdk.algosdk.transaction.model.AlgorandAddress

internal class AlgoSdkAddressImpl(
    private val base64Manager: Base64Manager
) : AlgoSdkAddress {

    override fun isValid(address: String): Boolean {
        return try {
            Sdk.isValidAddress(address)
        } catch (e: Exception) {
            false
        }
    }

    override fun generateAddressFromPublicKey(publicKey: ByteArray): AlgorandAddress? {
        return try {
            val address = Sdk.generateAddressFromPublicKey(publicKey)
            AlgorandAddress(address, publicKey)
        } catch (exception: Exception) {
            null
        }
    }

    override fun generateAddressFromPublicKey(addressBase64: String): AlgorandAddress? {
        val publicKey = try {
            base64Manager.decode(addressBase64, Base64.DEFAULT)
        } catch (exception: Exception) {
            return null
        }
        return generateAddressFromPublicKey(publicKey)
    }
}
