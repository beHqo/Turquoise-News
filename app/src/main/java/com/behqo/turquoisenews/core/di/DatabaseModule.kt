package com.behqo.turquoisenews.core.di

import android.content.Context
import com.behqo.turquoisenews.core.data.local.DataStoreFactory
import com.behqo.turquoisenews.core.data.local.DatabaseFactory
import com.behqo.turquoisenews.core.data.local.LocalDatabase
import com.behqo.turquoisenews.feature.articles.data.local.dao.ArticlesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context) =
        DatabaseFactory.create(appContext)

    @Provides
    @Singleton
    fun provideArticlesDao(db: LocalDatabase): ArticlesDao = db.getArticlesDao()

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(@ApplicationContext appContext: Context) =
        DataStoreFactory.create(appContext)
}