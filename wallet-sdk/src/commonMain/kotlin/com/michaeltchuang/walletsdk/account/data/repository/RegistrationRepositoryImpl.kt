/*
package com.michaeltchuang.walletsdk.account.data.repository

import com.michaeltchuang.walletsdk.account.data.sharedpref.RegistrationSkipLocalSource
import com.michaeltchuang.walletsdk.account.domain.repository.core.RegistrationRepository

class RegistrationRepositoryImpl(
    private val registrationSkipLocalSource: RegistrationSkipLocalSource
) : RegistrationRepository {

    override fun setRegistrationSkipPreferenceAsSkipped() {
        registrationSkipLocalSource.saveData(true)
    }

    override fun getRegistrationSkipped(): Boolean {
        return registrationSkipLocalSource.getData(RegistrationSkipLocalSource.Companion.defaultRegisterSkipPreference)
    }
}*/
