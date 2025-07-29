package com.behqo.turquoisenews.core.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.behqo.turquoisenews.core.presentation.mapper.toLocalizedMessage
import com.behqo.turquoisenews.core.presentation.theme.TurquoiseNewsTheme
import com.behqo.turquoisenews.navigation.TurquoiseNewsNavHost
import kotlinx.coroutines.launch

@Composable
fun RootScreen() = TurquoiseNewsTheme {
    val navController = rememberNavController()
    val snackbarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(snackbarHost = { SnackbarHost(snackbarState) }) { pw ->
        TurquoiseNewsNavHost(
            modifier = Modifier.padding(pw),
            navController = navController,
            showSnackbar = { error ->
                coroutineScope.launch {
                    snackbarState.showSnackbar(context.getString(error.toLocalizedMessage()))
                }
            }
        )
    }
}