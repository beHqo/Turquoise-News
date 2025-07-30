package com.behqo.turquoisenews.feature.userprefs.domain.interactors

import com.behqo.turquoisenews.feature.userprefs.domain.model.Theme
import com.behqo.turquoisenews.feature.userprefs.domain.repository.IUserPreferencesRepository
import javax.inject.Inject

class UpdateThemeUseCase @Inject constructor(private val repo: IUserPreferencesRepository) {
    suspend operator fun invoke(theme: Theme) = repo.updateTheme(theme)
}