package com.michaeltchuang.walletsdk.core.foundation.network.model

import com.google.gson.annotations.SerializedName

internal data class Pagination<T>(
    @SerializedName("next") val next: String?,
    @SerializedName("results") val results: List<T>,
)
