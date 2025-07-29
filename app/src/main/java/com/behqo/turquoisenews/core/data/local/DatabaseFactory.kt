package com.behqo.turquoisenews.core.data.local

import android.content.Context
import androidx.room.Room

private const val DATABASE_NAME = "local_database"

object DatabaseFactory {
    fun create(appContext: Context): LocalDatabase =
        Room.databaseBuilder(appContext, LocalDatabase::class.java, DATABASE_NAME).build()
}