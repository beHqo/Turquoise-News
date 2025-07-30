package com.behqo.turquoisenews.core.presentation.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behqo.turquoisenews.feature.userprefs.domain.interactors.ObserveUserPreferencesUseCase
import com.behqo.turquoisenews.feature.userprefs.domain.model.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(observeUserPreferences: ObserveUserPreferencesUseCase) :
    ViewModel() {
    val userPreferences = observeUserPreferences().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), UserPreferences()
    )
}