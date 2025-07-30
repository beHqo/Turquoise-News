package com.behqo.turquoisenews.core.presentation.root

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.behqo.turquoisenews.core.presentation.mapper.toLocalizedMessage
import com.behqo.turquoisenews.core.presentation.theme.TurquoiseNewsTheme
import com.behqo.turquoisenews.feature.userprefs.domain.model.Theme
import com.behqo.turquoisenews.navigation.TurquoiseNewsNavHost
import kotlinx.coroutines.launch

@Composable
fun RootScreen(vm: RootViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val navController = rememberNavController()
    val snackbarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val userPreferences by vm.userPreferences.collectAsStateWithLifecycle()
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val isDarkThemeEnabled by remember {
        derivedStateOf {
            when (userPreferences.theme) {
                Theme.DARK -> true
                Theme.LIGHT -> false
                Theme.DEFAULT -> isSystemInDarkTheme
            }
        }
    }

    TurquoiseNewsTheme(
        darkTheme = isDarkThemeEnabled, dynamicColor = userPreferences.dynamicColorsEnabled
    ) {
        Scaffold(snackbarHost = { SnackbarHost(snackbarState) }) { pw ->
            TurquoiseNewsNavHost(
                modifier = Modifier.Companion.padding(pw),
                navController = navController,
                showSnackbar = { error ->
                    coroutineScope.launch {
                        snackbarState.showSnackbar(context.getString(error.toLocalizedMessage()))
                    }
                })
        }
    }

}