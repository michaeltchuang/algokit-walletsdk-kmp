package com.michaeltchuang.walletsdk.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountInformationResponse(
    @SerialName("account")
    val accountInformation: AccountInformation? = null,
    @SerialName("current-round")
    val currentRound: Long? = null,
)

@Serializable
data class AccountInformation(
    @SerialName("address")
    val address: String,
    @SerialName("amount")
    val amount: String,
    @SerialName("amount-without-pending-rewards")
    val amountWithoutPendingRewards: Long,
    @SerialName("min-balance")
    val minBalance: Long,
    @SerialName("pending-rewards")
    val pendingRewards: Long,
    @SerialName("reward-base")
    val rewardBase: Long? = null,
    @SerialName("rewards")
    val rewards: Long,
    @SerialName("round")
    val round: Long,
    @SerialName("status")
    val status: String,
    @SerialName("sig-type")
    val sigType: String? = null,
    @SerialName("auth-addr")
    val authAddr: String? = null,
    @SerialName("assets")
    val assets: List<AssetHolding>? = null,
    @SerialName("apps-local-state")
    val appsLocalState: List<ApplicationLocalState>? = null,
    @SerialName("apps-total-schema")
    val appsTotalSchema: ApplicationStateSchema? = null,
    @SerialName("created-apps")
    val createdApps: List<Application>? = null,
    @SerialName("created-assets")
    val createdAssets: List<Asset>? = null,
    @SerialName("participation")
    val participation: AccountParticipation? = null,
)

@Serializable
data class AssetHolding(
    @SerialName("amount")
    val amount: Long,
    @SerialName("asset-id")
    val assetId: Long,
    @SerialName("is-frozen")
    val isFrozen: Boolean,
)

@Serializable
data class ApplicationLocalState(
    @SerialName("id")
    val id: Long,
    @SerialName("key-value")
    val keyValue: List<TealKeyValue>? = null,
    @SerialName("schema")
    val schema: ApplicationStateSchema? = null,
)

@Serializable
data class ApplicationStateSchema(
    @SerialName("num-byte-slice")
    val numByteSlice: Long,
    @SerialName("num-uint")
    val numUint: Long,
)

@Serializable
data class Application(
    @SerialName("id")
    val id: Long,
    @SerialName("params")
    val params: ApplicationParams,
)

@Serializable
data class ApplicationParams(
    @SerialName("approval-program")
    val approvalProgram: String,
    @SerialName("clear-state-program")
    val clearStateProgram: String,
    @SerialName("creator")
    val creator: String,
    @SerialName("global-state")
    val globalState: List<TealKeyValue>? = null,
    @SerialName("global-state-schema")
    val globalStateSchema: ApplicationStateSchema? = null,
    @SerialName("local-state-schema")
    val localStateSchema: ApplicationStateSchema? = null,
)

@Serializable
data class Asset(
    @SerialName("index")
    val index: Long,
    @SerialName("params")
    val params: AssetParams,
)

@Serializable
data class AssetParams(
    @SerialName("creator")
    val creator: String,
    @SerialName("decimals")
    val decimals: Int,
    @SerialName("default-frozen")
    val defaultFrozen: Boolean? = null,
    @SerialName("manager")
    val manager: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("name-b64")
    val nameB64: String? = null,
    @SerialName("reserve")
    val reserve: String? = null,
    @SerialName("total")
    val total: Long,
    @SerialName("unit-name")
    val unitName: String? = null,
    @SerialName("unit-name-b64")
    val unitNameB64: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("url-b64")
    val urlB64: String? = null,
)

@Serializable
data class TealKeyValue(
    @SerialName("key")
    val key: String,
    @SerialName("value")
    val value: TealValue,
)

@Serializable
data class TealValue(
    @SerialName("bytes")
    val bytes: String? = null,
    @SerialName("type")
    val type: Int,
    @SerialName("uint")
    val uint: Long? = null,
)

@Serializable
data class AccountParticipation(
    @SerialName("selection-participation-key")
    val selectionParticipationKey: String? = null,
    @SerialName("vote-first-valid")
    val voteFirstValid: Long? = null,
    @SerialName("vote-key-dilution")
    val voteKeyDilution: Long? = null,
    @SerialName("vote-last-valid")
    val voteLastValid: Long? = null,
    @SerialName("vote-participation-key")
    val voteParticipationKey: String? = null,
)
