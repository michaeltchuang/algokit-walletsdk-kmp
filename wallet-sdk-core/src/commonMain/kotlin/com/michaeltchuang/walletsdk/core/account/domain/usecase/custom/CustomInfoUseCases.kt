package com.michaeltchuang.walletsdk.core.account.domain.usecase.custom

import com.michaeltchuang.walletsdk.core.account.domain.model.custom.AccountOrderIndex
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomAccountInfo
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomHdSeedInfo
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.HdSeedOrderIndex
import kotlinx.coroutines.flow.Flow

fun interface SetAccountCustomName {
    suspend operator fun invoke(
        address: String,
        name: String,
    )
}

fun interface GetAccountCustomName {
    suspend operator fun invoke(address: String): String?
}

fun interface GetAccountsCustomInfoFlow {
    operator fun invoke(addresses: List<String>): Flow<Map<String, CustomAccountInfo?>>
}

fun interface GetAccountsCustomInfo {
    suspend operator fun invoke(addresses: List<String>): Map<String, CustomAccountInfo?>
}

fun interface SetAccountCustomInfo {
    suspend operator fun invoke(customInfo: CustomAccountInfo)
}

fun interface GetAccountCustomInfoOrNull {
    suspend operator fun invoke(address: String): CustomAccountInfo?
}

fun interface DeleteAccountCustomInfo {
    suspend operator fun invoke(address: String)
}

fun interface GetAccountCustomInfo {
    suspend operator fun invoke(address: String): CustomAccountInfo
}

fun interface SetAccountOrderIndex {
    suspend operator fun invoke(
        address: String,
        orderIndex: Int,
    )
}

fun interface GetBackedUpAccounts {
    suspend operator fun invoke(): Set<String>
}

fun interface GetNotBackedUpAccounts {
    suspend operator fun invoke(): Set<String>
}

fun interface GetAccountBackUpStatus {
    suspend operator fun invoke(accountAddress: String): Boolean
}

fun interface SetAddressesBackedUp {
    suspend operator fun invoke(accountAddresses: Set<String>)
}

fun interface GetAllAccountOrderIndexes {
    suspend operator fun invoke(): List<AccountOrderIndex>
}

fun interface ClearAllCustomInformation {
    suspend operator fun invoke()
}

// custom_hd_seed_info

fun interface SetHdSeedCustomName {
    suspend operator fun invoke(
        seedId: Int,
        name: String,
    )
}

fun interface GetHdSeedCustomName {
    suspend operator fun invoke(seedId: Int): String?
}

fun interface SetHdSeedCustomInfo {
    suspend operator fun invoke(customInfo: CustomHdSeedInfo)
}

fun interface GetHdSeedCustomInfoOrNull {
    suspend operator fun invoke(seedId: Int): CustomHdSeedInfo?
}

fun interface DeleteHdSeedCustomInfo {
    suspend operator fun invoke(seedId: Int)
}

fun interface GetHdSeedCustomInfo {
    suspend operator fun invoke(seedId: Int): CustomHdSeedInfo
}

fun interface SetHdSeedOrderIndex {
    suspend operator fun invoke(
        seedId: Int,
        orderIndex: Int,
    )
}

fun interface GetBackedUpHdSeeds {
    suspend operator fun invoke(): Set<String>
}

fun interface GetNotBackedUpHdSeeds {
    suspend operator fun invoke(): Set<String>
}

fun interface GetHdSeedAsbBackUpStatus {
    suspend operator fun invoke(seedId: Int): Boolean
}

fun interface GetAllHdSeedOrderIndexes {
    suspend operator fun invoke(): List<HdSeedOrderIndex>
}
