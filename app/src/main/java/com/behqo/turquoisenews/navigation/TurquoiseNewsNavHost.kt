package com.behqo.turquoisenews.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.behqo.turquoisenews.core.domain.model.Error
import com.behqo.turquoisenews.feature.articles.presentation.article_details.ArticleDetailsScreen
import com.behqo.turquoisenews.feature.articles.presentation.article_list.ArticleListScreen
import com.behqo.turquoisenews.feature.userprefs.presentation.SettingsScreen

@Composable
fun TurquoiseNewsNavHost(
    modifier: Modifier = Modifier, navController: NavHostController, showSnackbar: (Error) -> Unit
) = NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = NavigationRoute.ArticleListScreen
) {
    composable<NavigationRoute.ArticleListScreen> {
        ArticleListScreen(
            navigateToArticleDetails = { id ->
                navController.navigate(NavigationRoute.ArticleDetails(id))
            },
            navigateToSettingScreen = { navController.navigate(NavigationRoute.SettingScreen) },
            showSnackbar = showSnackbar
        )
    }

    composable<NavigationRoute.ArticleDetails> {
        ArticleDetailsScreen(showSnackbar = showSnackbar, navigateUp = navController::navigateUp)
    }

    composable<NavigationRoute.SettingScreen> {
        SettingsScreen(navigateUp = navController::navigateUp)
    }
}