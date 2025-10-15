package com.michaeltchuang.walletsdk.core.account.domain.repository.core

interface RegistrationRepository {
    fun setRegistrationSkipPreferenceAsSkipped()

    fun getRegistrationSkipped(): Boolean
}
