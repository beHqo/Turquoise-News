package com.behqo.turquoisenews.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    object ArticleListScreen : NavigationRoute

    @Serializable
    data class ArticleDetails(val articleId: Long) : NavigationRoute

    @Serializable
    object SettingScreen : NavigationRoute
}