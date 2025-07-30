package com.behqo.turquoisenews.feature.userprefs.domain.interactors

import com.behqo.turquoisenews.feature.userprefs.domain.model.UserPreferences
import com.behqo.turquoisenews.feature.userprefs.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserPreferencesUseCase @Inject constructor(private val repo: IUserPreferencesRepository) {
    operator fun invoke(): Flow<UserPreferences> = repo.observeUserPreferences()
}