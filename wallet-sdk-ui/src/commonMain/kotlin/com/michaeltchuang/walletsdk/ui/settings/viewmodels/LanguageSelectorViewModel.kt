package com.michaeltchuang.walletsdk.ui.settings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.core.foundation.EventDelegate
import com.michaeltchuang.walletsdk.core.foundation.EventViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel
import com.michaeltchuang.walletsdk.ui.settings.domain.localization.LocalizationPreference
import com.michaeltchuang.walletsdk.ui.settings.domain.localization.LocalizationPreferenceRepository
import kotlinx.coroutines.launch

class LanguageSelectorViewModel(
    private val localizationPreferenceRepository: LocalizationPreferenceRepository,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<LanguageSelectorViewModel.ViewState> by stateDelegate,
    EventViewModel<LanguageSelectorViewModel.ViewEvent> by eventDelegate {
    init {
        stateDelegate.setDefaultState(ViewState.Loading)
        loadCurrentLocalizationPreference()
    }

    private fun loadCurrentLocalizationPreference() {
        viewModelScope.launch {
            localizationPreferenceRepository
                .getSavedLocalizationPreferenceFlow()
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
                // This will trigger a recomposition in all composables that use localizedStringResource()
                // because they read LocalAppLocale, which is provided via CompositionLocalProvider
                // in AlgoKitTheme and updates when this flow emits a new value
                localizationPreferenceRepository.saveLocalizationPreference(language)
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
