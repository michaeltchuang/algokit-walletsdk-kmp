package com.michaeltchuang.walletsdk.account.domain.usecase.recoverypassphrase

import com.michaeltchuang.walletsdk.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.account.domain.model.core.OnboardingAccountType
import com.michaeltchuang.walletsdk.algosdk.AlgoKitBip39.getEntropyFromMnemonic
import com.michaeltchuang.walletsdk.algosdk.recoverAlgo25Account
import com.michaeltchuang.walletsdk.utils.CreationType
import com.michaeltchuang.walletsdk.utils.toShortenedAddress
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.flow.flow


@Suppress("LongParameterList")
class RecoverPassphraseUseCase() {

    fun validateEnteredMnemonics(
        mnemonics: String,
        onboardingAccountType: OnboardingAccountType
    ) = flow {
        val accountAddress = ""
        val recoveredAccount = getAccount(onboardingAccountType, mnemonics, accountAddress)
        emit(recoveredAccount)
    }


    private suspend fun getAccount(
        accountType: OnboardingAccountType,
        mnemonics: String,
        accountAddress: String
    ): AccountCreation? {
        return when (accountType) {
            OnboardingAccountType.Algo25 -> {
                val algo25account = recoverAlgo25Account(
                    mnemonics.lowercase()
                ) ?: return null
                AccountCreation(
                    address = algo25account.address,
                    customName = algo25account.address.toShortenedAddress(),
                    isBackedUp = true,
                    type = AccountCreation.Type.Algo25(
                        algo25account.secretKey
                    ),
                    creationType = CreationType.RECOVER
                )
            }

            OnboardingAccountType.HdKey -> {
                // only entropy is needed for next screen (importing registered addresses)
                val entropy = getEntropyFromMnemonic(mnemonics) ?: return null
                AccountCreation(
                    address = accountAddress,
                    customName = accountAddress.toShortenedAddress(),
                    isBackedUp = true,
                    type = AccountCreation.Type.HdKey(
                        publicKey = ByteArray(0),
                        encryptedPrivateKey = ByteArray(0),
                        encryptedEntropy = entropy.toByteArray(),
                        account = 0,
                        change = 0,
                        keyIndex = 0,
                        derivationType = 0
                    ),
                    creationType = CreationType.RECOVER
                )
            }
        }
    }
}
