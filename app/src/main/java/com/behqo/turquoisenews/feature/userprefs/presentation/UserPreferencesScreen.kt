package com.behqo.turquoisenews.feature.userprefs.presentation

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.behqo.turquoisenews.R
import com.behqo.turquoisenews.core.presentation.common.DetailsTopBar
import com.behqo.turquoisenews.core.presentation.theme.PaddingManager
import com.behqo.turquoisenews.feature.userprefs.domain.model.Theme
import com.behqo.turquoisenews.feature.userprefs.domain.model.UserPreferences

@Composable
fun SettingsScreen(vm: UserPreferencesViewModel = hiltViewModel(), navigateUp: () -> Unit) {
    val userPreferences by vm.userPreferences.collectAsStateWithLifecycle()

    SettingsScreen(
        userPreferences = userPreferences,
        toggleDynamicColors = vm::toggleDynamicColors,
        setTheme = vm::setTheme,
        navigateUp = navigateUp
    )
}

@Composable
private fun SettingsScreen(
    userPreferences: UserPreferences,
    toggleDynamicColors: (Boolean) -> Unit,
    setTheme: (Theme) -> Unit,
    navigateUp: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DetailsTopBar(
            title = stringResource(R.string.settings_top_bar_title), onNavigateUp = navigateUp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = PaddingManager.extraLarge,
                    end = PaddingManager.extraLarge,
                    top = PaddingManager.extraLarge,
                    bottom = PaddingManager.large
                )
                .toggleable(
                    value = userPreferences.dynamicColorsEnabled,
                    onValueChange = toggleDynamicColors
                ), verticalAlignment = Alignment.CenterVertically
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Text(
                    text = stringResource(R.string.settings_dynamic_colors),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = userPreferences.dynamicColorsEnabled,
                    onCheckedChange = toggleDynamicColors
                )
            }
        }

        Text(
            text = stringResource(R.string.settings_theme),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = PaddingManager.extraLarge,
                    end = PaddingManager.extraLarge,
                    bottom = PaddingManager.large
                )
        )

        Theme.entries.forEach { themeOption ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = PaddingManager.extraLarge,
                        end = PaddingManager.extraLarge,
                        bottom = PaddingManager.small
                    )
                    .selectable(
                        selected = (userPreferences.theme == themeOption),
                        onClick = { setTheme(themeOption) }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (userPreferences.theme == themeOption),
                    onClick = { setTheme(themeOption) })
                Spacer(modifier = Modifier.width(PaddingManager.large))
                Text(
                    text = themeOption.name.lowercase().replaceFirstChar { it.titlecase() },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}