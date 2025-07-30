package com.behqo.turquoisenews.core.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesKeys {
    private const val DYNAMIC_COLORS_KEY = "dynamic_colors"
    private const val THEME_KEY = "theme"

    val THEME = stringPreferencesKey(THEME_KEY)
    val DYNAMIC_COLORS = booleanPreferencesKey(DYNAMIC_COLORS_KEY)
}