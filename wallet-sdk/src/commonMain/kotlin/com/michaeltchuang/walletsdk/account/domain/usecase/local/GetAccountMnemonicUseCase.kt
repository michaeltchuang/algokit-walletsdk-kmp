package com.michaeltchuang.walletsdk.account.domain.usecase.local

import com.michaeltchuang.walletsdk.account.domain.model.local.AccountMnemonic
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.core.GetLocalAccountUseCase
import com.michaeltchuang.walletsdk.algosdk.AlgoKitBip39.getMnemonicFromEntropy
import com.michaeltchuang.walletsdk.algosdk.getMnemonicFromAlgo25SecretKey
import com.michaeltchuang.walletsdk.foundation.AlgoKitResult
import com.michaeltchuang.walletsdk.utils.splitMnemonic

internal class GetAccountMnemonicUseCase(
    private val getLocalAccount: GetLocalAccountUseCase,
    private val getAlgo25SecretKey: GetAlgo25SecretKey,
    private val getHdEntropy: GetHdEntropy,
) : GetAccountMnemonic {
    override suspend fun invoke(address: String): AlgoKitResult<AccountMnemonic> {
        val localAccount = getLocalAccount(address)
        return when (localAccount) {
            is LocalAccount.Algo25 -> getAlgo25Mnemonic(address)
            is LocalAccount.HdKey -> getHdKeyMnemonic(address)
            else -> AlgoKitResult.Error(IllegalArgumentException())
        }
    }

    private suspend fun getAlgo25Mnemonic(address: String): AlgoKitResult<AccountMnemonic> {
        val secretKey =
            getAlgo25SecretKey(address) ?: return AlgoKitResult.Error(IllegalArgumentException())
        val mnemonic =
            getMnemonicFromAlgo25SecretKey(secretKey) ?: return AlgoKitResult.Error(
                IllegalArgumentException(),
            )
        return getAccountMnemonic(mnemonic, AccountMnemonic.AccountType.Algo25)
    }

    private suspend fun getHdKeyMnemonic(address: String): AlgoKitResult<AccountMnemonic> {
        val localAccount = getLocalAccount(address)
        if (localAccount !is LocalAccount.HdKey) {
            return AlgoKitResult.Error(IllegalArgumentException("Account is not an HD key account."))
        }

        val entropy =
            getHdEntropy(localAccount.seedId) ?: return AlgoKitResult.Error(
                IllegalArgumentException("HD entropy not found for seed"),
            )

        val mnemonic = getMnemonicFromEntropy(entropy)
        return getAccountMnemonic(mnemonic, AccountMnemonic.AccountType.HdKey)
    }

    private fun getAccountMnemonic(
        mnemonic: String?,
        type: AccountMnemonic.AccountType,
    ): AlgoKitResult<AccountMnemonic> =
        if (mnemonic.isNullOrBlank()) {
            AlgoKitResult.Error(IllegalArgumentException())
        } else {
            val mnemonicWords = mnemonic.splitMnemonic()
            AlgoKitResult.Success(AccountMnemonic(mnemonicWords, type))
        }
}
