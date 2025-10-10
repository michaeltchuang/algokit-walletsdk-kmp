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
            /*   Algo25 -> getAlgo25Signer(address)
               AccountType.HdKey -> getHdKeySigner(address)
               LedgerBle -> getLedgerSigner(address)
               NoAuth -> SignerNotFound.NoAuth(address)
               Rekeyed -> SignerNotFound.NoAuth(address)
               RekeyedAuth -> getRekeyedAuthSigner(accountDetail, address)
               null -> AccountNotFound(address)*/
            is LocalAccount.Algo25 -> getAlgo25Signer(address)
            is LocalAccount.HdKey -> getHdKeySigner(address)
            is LocalAccount.NoAuth -> TransactionSigner.SignerNotFound.NoAuth(address)
            else -> {
                TransactionSigner.SignerNotFound.AccountNotFound(address)
            }
        }
    }

    private fun getAlgo25Signer(address: String): TransactionSigner {
        return TransactionSigner.Algo25(address)
    }

    private fun getHdKeySigner(address: String): TransactionSigner {
        return TransactionSigner.HdKey(address)
    }

}