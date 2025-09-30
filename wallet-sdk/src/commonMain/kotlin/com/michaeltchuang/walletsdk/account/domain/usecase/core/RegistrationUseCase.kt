package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.repository.core.RegistrationRepository

class RegistrationUseCase(
    private val registrationRepository: RegistrationRepository,
) {
    fun setRegistrationSkipPreferenceAsSkipped() {
        registrationRepository.setRegistrationSkipPreferenceAsSkipped()
    }

    fun getRegistrationSkipped(): Boolean = registrationRepository.getRegistrationSkipped()
}
