package com.behqo.turquoisenews.feature.userprefs.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.behqo.turquoisenews.core.data.local.UserPreferencesKeys
import com.behqo.turquoisenews.feature.userprefs.domain.model.Theme
import com.behqo.turquoisenews.feature.userprefs.domain.model.UserPreferences
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

private const val DATASTORE_FILE_NAME = "test_datastore.preferences_pb"

class UserPreferencesRepositoryTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @get:Rule
    val tempFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testScope,
        produceFile = { tempFolder.newFile(DATASTORE_FILE_NAME) })
    private val repository = UserPreferencesRepository(testDataStore)

    @Test
    fun testInitialUserPreferences() = runTest(testDispatcher) {
        val expected = testDataStore.data.first().toUserPreferences()
        val actual = repository.observeUserPreferences().first()

        expected shouldBe actual
    }

    @Test
    fun toggleDynamicColors() = runTest(testDispatcher) {
        val dynamicColorsEnabled = true
        repository.toggleDynamicColors(dynamicColorsEnabled)

        testDataStore.edit { preferences ->
            preferences[UserPreferencesKeys.DYNAMIC_COLORS] = dynamicColorsEnabled
        }

        val expected = testDataStore.data.first()[UserPreferencesKeys.DYNAMIC_COLORS]
        val actual = repository.observeUserPreferences().first().dynamicColorsEnabled

        expected shouldBe actual
    }

    @Test
    fun updateTheme() = runTest(testDispatcher) {
        val darkTheme = Theme.DARK
        repository.updateTheme(darkTheme)

        testDataStore.edit { preferences ->
            preferences[UserPreferencesKeys.THEME] = darkTheme.name
        }

        val expected = testDataStore.data.first()[UserPreferencesKeys.THEME]
        val actual = repository.observeUserPreferences().first().theme.name

        expected shouldBe actual
    }

    private fun Preferences.toUserPreferences(): UserPreferences = UserPreferences(
        theme = Theme.valueOf(this[UserPreferencesKeys.THEME] ?: Theme.DEFAULT.name),
        dynamicColorsEnabled = this[UserPreferencesKeys.DYNAMIC_COLORS] ?: false,
    )
}