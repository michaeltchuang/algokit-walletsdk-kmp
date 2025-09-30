package com.michaeltchuang.walletsdk.account.domain.repository.core

interface RegistrationRepository {
    fun setRegistrationSkipPreferenceAsSkipped()

    fun getRegistrationSkipped(): Boolean
}
