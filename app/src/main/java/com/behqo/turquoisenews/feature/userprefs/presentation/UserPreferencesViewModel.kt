package com.behqo.turquoisenews.feature.userprefs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behqo.turquoisenews.feature.userprefs.domain.interactors.ObserveUserPreferencesUseCase
import com.behqo.turquoisenews.feature.userprefs.domain.interactors.ToggleDynamicColorsUseCase
import com.behqo.turquoisenews.feature.userprefs.domain.interactors.UpdateThemeUseCase
import com.behqo.turquoisenews.feature.userprefs.domain.model.Theme
import com.behqo.turquoisenews.feature.userprefs.domain.model.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPreferencesViewModel @Inject constructor(
    observeUserPreferences: ObserveUserPreferencesUseCase,
    private val updateTheme: UpdateThemeUseCase,
    private val toggleDynamicColorsUseCase: ToggleDynamicColorsUseCase
) : ViewModel() {
    val userPreferences = observeUserPreferences()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UserPreferences())

    fun setTheme(theme: Theme) {
        viewModelScope.launch { updateTheme(theme) }
    }

    fun toggleDynamicColors(enabled: Boolean) {
        viewModelScope.launch { toggleDynamicColorsUseCase(enabled) }
    }
}