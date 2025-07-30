package com.behqo.turquoisenews.feature.userprefs.domain.model

data class UserPreferences(
    val dynamicColorsEnabled: Boolean = false,
    val theme: Theme = Theme.DEFAULT
)