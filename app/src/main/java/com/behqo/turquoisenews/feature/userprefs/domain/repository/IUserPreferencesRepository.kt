package com.behqo.turquoisenews.feature.userprefs.domain.repository

import com.behqo.turquoisenews.feature.userprefs.domain.model.Theme
import com.behqo.turquoisenews.feature.userprefs.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface IUserPreferencesRepository {
    fun observeUserPreferences(): Flow<UserPreferences>
    suspend fun toggleDynamicColors(enabled: Boolean)
    suspend fun updateTheme(theme: Theme)
}