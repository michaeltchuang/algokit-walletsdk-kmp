package com.michaeltchuang.walletsdk.account.domain.usecase.recoverypassphrase

import com.michaeltchuang.walletsdk.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.account.domain.model.core.OnboardingAccountType
import com.michaeltchuang.walletsdk.algosdk.AlgoKitBip39.getEntropyFromMnemonic
import com.michaeltchuang.walletsdk.algosdk.getBip39Wallet
import com.michaeltchuang.walletsdk.algosdk.recoverAlgo25Account
import com.michaeltchuang.walletsdk.utils.CreationType
import com.michaeltchuang.walletsdk.utils.toShortenedAddress
import kotlinx.coroutines.flow.flow

@Suppress("LongParameterList")
class RecoverPassphraseUseCase {
    fun validateEnteredMnemonics(
        mnemonics: String,
        onboardingAccountType: OnboardingAccountType,
    ) = flow {
        val recoveredAccount = getAccount(onboardingAccountType, mnemonics)
        emit(recoveredAccount)
    }

    private fun getAccount(
        accountType: OnboardingAccountType,
        mnemonics: String,
    ): AccountCreation? {
        return when (accountType) {
            OnboardingAccountType.Algo25 -> {
                val algo25account =
                    recoverAlgo25Account(
                        mnemonics.lowercase(),
                    ) ?: return null
                AccountCreation(
                    address = algo25account.address,
                    customName = algo25account.address.toShortenedAddress(),
                    isBackedUp = false,
                    type =
                        AccountCreation.Type.Algo25(
                            algo25account.secretKey,
                        ),
                    creationType = CreationType.RECOVER,
                )
            }

            OnboardingAccountType.Falcon24 -> {
                val entropy = getEntropyFromMnemonic(mnemonics)
                val wallet = getBip39Wallet(entropy)
                val falcon24 = wallet.generateFalcon24Address(mnemonics)
                AccountCreation(
                    address = falcon24.address,
                    customName = falcon24.address.toShortenedAddress(),
                    isBackedUp = false,
                    type =
                        AccountCreation.Type.Falcon24(
                            publicKey = falcon24.publicKey,
                            encryptedPrivateKey = falcon24.privateKey,
                            encryptedEntropy = entropy,
                        ),
                    creationType = CreationType.RECOVER,
                )
            }
            else -> {
                null
            }
        }
    }
}
