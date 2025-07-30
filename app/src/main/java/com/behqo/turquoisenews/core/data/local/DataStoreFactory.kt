package com.behqo.turquoisenews.core.data.local

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile

private const val DATASTORE_FILE_NAME = "user_preferences"

object DataStoreFactory {
    fun create(appContext: Context) =
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }),
            produceFile = { appContext.preferencesDataStoreFile(DATASTORE_FILE_NAME) })
}