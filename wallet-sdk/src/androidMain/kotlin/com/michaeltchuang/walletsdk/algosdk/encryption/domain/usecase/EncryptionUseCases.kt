 

package com.michaeltchuang.walletsdk.algosdk.encryption.domain.usecase

import javax.crypto.SecretKey

fun interface GetEncryptionSecretKey {
    operator fun invoke(): SecretKey
}
