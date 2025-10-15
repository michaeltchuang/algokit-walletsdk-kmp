package com.michaeltchuang.walletsdk.core.encryption.domain.usecase

import javax.crypto.SecretKey

fun interface GetEncryptionSecretKey {
    operator fun invoke(): SecretKey
}
