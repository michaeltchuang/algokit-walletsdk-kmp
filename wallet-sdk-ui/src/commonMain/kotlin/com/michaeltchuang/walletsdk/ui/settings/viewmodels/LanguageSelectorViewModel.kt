package com.michaeltchuang.walletsdk.ui.settings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.core.foundation.EventDelegate
import com.michaeltchuang.walletsdk.core.foundation.EventViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel
import com.michaeltchuang.walletsdk.ui.settings.domain.localization.LocalizationPreference
import com.michaeltchuang.walletsdk.ui.settings.domain.localization.LocalizationPreferenceRepository
import com.michaeltchuang.walletsdk.ui.settings.domain.theme.ThemePreference
import com.michaeltchuang.walletsdk.ui.settings.domain.theme.ThemePreferenceRepository
import kotlinx.coroutines.launch

class LanguageSelectorViewModel(
    private val localizationPreferenceRepository: LocalizationPreferenceRepository,
    private val themePreferenceRepository: ThemePreferenceRepository,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<LanguageSelectorViewModel.ViewState> by stateDelegate,
    EventViewModel<LanguageSelectorViewModel.ViewEvent> by eventDelegate {

    private var currentTheme: ThemePreference = ThemePreference.SYSTEM

    init {
        stateDelegate.setDefaultState(ViewState.Loading)
        loadCurrentLocalizationPreference()
        observeCurrentTheme()
    }

    private fun observeCurrentTheme() {
        viewModelScope.launch {
            themePreferenceRepository.getSavedThemePreferenceFlow().collect { theme ->
                currentTheme = theme
            }
        }
    }

    private fun loadCurrentLocalizationPreference() {
        viewModelScope.launch {
            localizationPreferenceRepository.getSavedLocalizationPreferenceFlow()
                .collect { localizationPreference ->
                    stateDelegate.updateState {
                        ViewState.Content(
                            currentLanguage = localizationPreference,
                            languageOptions = LocalizationPreference.entries,
                        )
                    }
                }
        }
    }

    fun onLanguageSelected(language: LocalizationPreference) {
        viewModelScope.launch {
            try {
                // Save the language preference
                localizationPreferenceRepository.saveLocalizationPreference(language)
                // Force recomposition by re-saving the theme
                // On Android: This triggers the Activity's observeLanguageChanges() via the language preference change,
                //             which calls recreate(). The theme re-save ensures immediate UI feedback.
                // On iOS: This forces a recomposition cascade that updates all stringResource() calls
                themePreferenceRepository.saveThemePreference(currentTheme)
                eventDelegate.sendEvent(ViewEvent.LanguageChanged(language))
            } catch (e: Exception) {
                displayError(e.message ?: "Failed to save language preference")
            }
        }
    }

    private fun displayError(message: String) {
        viewModelScope.launch {
            eventDelegate.sendEvent(ViewEvent.Error(message))
        }
    }

    sealed interface ViewState {
        data object Idle : ViewState

        data object Loading : ViewState

        data class Content(
            val currentLanguage: LocalizationPreference,
            val languageOptions: List<LocalizationPreference>,
        ) : ViewState
    }

    sealed interface ViewEvent {
        data class LanguageChanged(
            val language: LocalizationPreference,
        ) : ViewEvent

        data class Error(
            val message: String,
        ) : ViewEvent
    }
}
