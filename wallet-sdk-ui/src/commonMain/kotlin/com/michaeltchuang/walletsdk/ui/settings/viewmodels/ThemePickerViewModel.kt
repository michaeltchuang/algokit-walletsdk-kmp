package com.michaeltchuang.walletsdk.ui.settings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.core.foundation.EventDelegate
import com.michaeltchuang.walletsdk.core.foundation.EventViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel
import com.michaeltchuang.walletsdk.ui.settings.domain.theme.ThemePreference
import com.michaeltchuang.walletsdk.ui.settings.domain.theme.ThemePreferenceRepository
import kotlinx.coroutines.launch

class ThemePickerViewModel(
    private val themePreferenceRepository: ThemePreferenceRepository,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<ThemePickerViewModel.ViewState> by stateDelegate,
    EventViewModel<ThemePickerViewModel.ViewEvent> by eventDelegate {
    init {
        stateDelegate.setDefaultState(ViewState.Loading)
        loadCurrentThemePreference()
    }

    private fun loadCurrentThemePreference() {
        viewModelScope.launch {
            themePreferenceRepository.getSavedThemePreferenceFlow().collect { themePreference ->
                stateDelegate.updateState {
                    ViewState.Content(
                        currentTheme = themePreference,
                        themeOptions = ThemePreference.entries,
                    )
                }
            }
        }
    }

    fun onThemeSelected(theme: ThemePreference) {
        viewModelScope.launch {
            try {
                themePreferenceRepository.saveThemePreference(theme)
                eventDelegate.sendEvent(ViewEvent.ThemeChanged(theme))
            } catch (e: Exception) {
                displayError(e.message ?: "Failed to save theme preference")
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
            val currentTheme: ThemePreference,
            val themeOptions: List<ThemePreference>,
        ) : ViewState
    }

    sealed interface ViewEvent {
        data class ThemeChanged(
            val theme: ThemePreference,
        ) : ViewEvent

        data class Error(
            val message: String,
        ) : ViewEvent
    }
}
