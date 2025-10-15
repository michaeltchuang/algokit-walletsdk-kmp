package com.michaeltchuang.walletsdk.core.foundation.utils.manager

import com.michaeltchuang.walletsdk.core.account.domain.model.core.AccountCreation

/**
 * Singleton manager for temporarily storing AccountCreation objects
 * during navigation between screens. This avoids serialization issues with complex
 * objects containing ByteArrays that cannot be passed through Android's savedStateHandle.
 *
 * Usage:
 * - Store: AccountCreationManager.storePendingAccountCreation(accountCreation)
 * - Retrieve: AccountCreationManager.getPendingAccountCreation()
 * - Clear: AccountCreationManager.clearPendingAccountCreation()
 */
object AccountCreationManager {
    private var pendingAccountCreation: AccountCreation? = null

    /**
     * Stores an AccountCreation object temporarily for navigation purposes
     */
    fun storePendingAccountCreation(accountCreation: AccountCreation) {
        pendingAccountCreation = accountCreation
    }

    /**
     * Retrieves the currently stored AccountCreation object
     * @return AccountCreation if one is stored, null otherwise
     */
    fun getPendingAccountCreation(): AccountCreation? = pendingAccountCreation

    /**
     * Clears the currently stored AccountCreation object
     * Should be called after the account creation process is complete
     */
    fun clearPendingAccountCreation() {
        pendingAccountCreation = null
    }

    /**
     * Checks if there's a pending AccountCreation object
     * @return true if an AccountCreation is stored, false otherwise
     */
    fun hasPendingAccountCreation(): Boolean = pendingAccountCreation != null
}
