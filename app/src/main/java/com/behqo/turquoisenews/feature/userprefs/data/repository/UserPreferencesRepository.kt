package com.behqo.turquoisenews.feature.userprefs.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.behqo.turquoisenews.core.data.local.UserPreferencesKeys
import com.behqo.turquoisenews.feature.userprefs.domain.model.Theme
import com.behqo.turquoisenews.feature.userprefs.domain.model.UserPreferences
import com.behqo.turquoisenews.feature.userprefs.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "UserPreferencesRepo"

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) :
    IUserPreferencesRepository {
    override fun observeUserPreferences(): Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { it.toUserPreferences() }

    override suspend fun toggleDynamicColors(enabled: Boolean) =
        safeUpdate("Given value: $enabled") { it[UserPreferencesKeys.DYNAMIC_COLORS] = enabled }

    override suspend fun updateTheme(theme: Theme) =
        safeUpdate("Given Theme = ${theme.name}") { it[UserPreferencesKeys.THEME] = theme.name }

    private fun Preferences.toUserPreferences(): UserPreferences = UserPreferences(
        dynamicColorsEnabled = this[UserPreferencesKeys.DYNAMIC_COLORS] ?: false,
        theme = Theme.valueOf(this[UserPreferencesKeys.THEME] ?: Theme.DEFAULT.name)
    )

    private suspend fun safeUpdate(logMessage: String, operation: (MutablePreferences) -> Unit) {
        try {
            dataStore.edit { operation(it) }
        } catch (e: IOException) {
            Log.e(
                TAG,
                "safeUpdate: Failed to update UserPreferences with the given value:\n$logMessage",
                e
            )
        }
    }
}