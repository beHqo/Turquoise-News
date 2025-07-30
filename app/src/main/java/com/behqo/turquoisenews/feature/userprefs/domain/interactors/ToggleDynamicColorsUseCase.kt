package com.behqo.turquoisenews.feature.userprefs.domain.interactors

import com.behqo.turquoisenews.feature.userprefs.domain.repository.IUserPreferencesRepository
import javax.inject.Inject

class ToggleDynamicColorsUseCase @Inject constructor(private val repo: IUserPreferencesRepository) {
    suspend operator fun invoke(enabled: Boolean) = repo.toggleDynamicColors(enabled)
}