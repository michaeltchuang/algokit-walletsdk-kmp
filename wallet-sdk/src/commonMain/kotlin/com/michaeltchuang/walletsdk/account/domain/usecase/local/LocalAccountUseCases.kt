package com.michaeltchuang.walletsdk.account.domain.usecase.local

import com.michaeltchuang.walletsdk.account.domain.model.core.AccountRegistrationType
import com.michaeltchuang.walletsdk.account.domain.model.local.AccountMnemonic
import com.michaeltchuang.walletsdk.account.domain.model.local.HdWalletSummary
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.foundation.AlgoKitResult
import com.michaeltchuang.walletsdk.network.model.TransactionSigner

internal fun interface SaveAlgo25Account {
    suspend operator fun invoke(
        account: LocalAccount.Algo25,
        privateKey: ByteArray,
    )
}

internal fun interface DeleteAlgo25Account {
    suspend operator fun invoke(address: String)
}

internal fun interface SaveFalcon24Account {
    suspend operator fun invoke(
        account: LocalAccount.Falcon24,
        seedId: Int,
        privateKey: ByteArray,
    )
}

internal fun interface DeleteFalcon24Account {
    suspend operator fun invoke(address: String)
}

internal fun interface DeleteHdKeyAccount {
    suspend operator fun invoke(address: String)
}

fun interface GetLocalAccounts {
    suspend operator fun invoke(): List<LocalAccount>
}

fun interface GetLocalAccount {
    suspend operator fun invoke(address: String): LocalAccount?
}

internal fun interface SaveHdKeyAccount {
    suspend operator fun invoke(
        account: LocalAccount.HdKey,
        privateKey: ByteArray,
    )
}

fun interface GetSeedIdIfExistingEntropy {
    suspend operator fun invoke(entropy: ByteArray): Int?
}

interface GetAccountRegistrationType {
    suspend operator fun invoke(address: String): AccountRegistrationType?

    operator fun invoke(account: LocalAccount): AccountRegistrationType
}

fun interface GetHdWalletSummaries {
    suspend operator fun invoke(): List<HdWalletSummary>?
}

fun interface GetMaxHdSeedId {
    suspend operator fun invoke(): Int?
}

fun interface GetHdEntropy {
    suspend operator fun invoke(seedId: Int): ByteArray?
}

fun interface GetAccountMnemonic {
    suspend operator fun invoke(address: String): AlgoKitResult<AccountMnemonic>
}

fun interface GetAlgo25SecretKey {
    suspend operator fun invoke(address: String): ByteArray?
}

fun interface GetFalcon24SecretKey {
    suspend operator fun invoke(address: String): ByteArray?
}

fun interface GetFalcon24WalletSummaries {
    suspend operator fun invoke(): List<HdWalletSummary>?
}

fun interface IsAccountRekeyedToAnotherAccount {
    suspend operator fun invoke(address: String): Boolean
}

fun interface GetAccountRekeyAdminAddress {
    suspend operator fun invoke(address: String): String?
}

fun interface GetTransactionSigner {
    suspend operator fun invoke(address: String): TransactionSigner
}

fun interface GetHdSeed {
    suspend operator fun invoke(seedId: Int): ByteArray?
}
