package com.behqo.turquoisenews.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.behqo.turquoisenews.feature.articles.data.local.dao.ArticlesDao
import com.behqo.turquoisenews.feature.articles.data.local.model.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
@TypeConverters(QueryTargetTypeConvertor::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getArticlesDao(): ArticlesDao
}