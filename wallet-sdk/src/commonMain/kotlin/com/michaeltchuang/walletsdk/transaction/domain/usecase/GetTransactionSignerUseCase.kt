package com.michaeltchuang.walletsdk.transaction.domain.usecase

import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetLocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetTransactionSigner
import com.michaeltchuang.walletsdk.network.model.TransactionSigner

internal class GetTransactionSignerUseCase(
    private val getAccountDetail: GetLocalAccount,
) : GetTransactionSigner {
    override suspend fun invoke(address: String): TransactionSigner {
        val accountDetail = getAccountDetail(address)
        return when (accountDetail) {
            is LocalAccount.Algo25 -> getAlgo25Signer(address)
            is LocalAccount.HdKey -> getHdKeySigner(address)
            is LocalAccount.Falcon24 -> getFalcon24Signer(address)
            is LocalAccount.NoAuth -> TransactionSigner.SignerNotFound.NoAuth(address)
            else -> {
                TransactionSigner.SignerNotFound.AccountNotFound(address)
            }
        }
    }

    private fun getAlgo25Signer(address: String): TransactionSigner = TransactionSigner.Algo25(address)

    private fun getHdKeySigner(address: String): TransactionSigner = TransactionSigner.HdKey(address)

    private fun getFalcon24Signer(address: String): TransactionSigner = TransactionSigner.Falcon24(address)
}
