package com.behqo.turquoisenews.feature.articles.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.behqo.turquoisenews.feature.articles.data.local.model.ArticleEntity
import com.behqo.turquoisenews.feature.articles.data.local.model.ArticleTableInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Query("SELECT * FROM ${ArticleTableInfo.TABLE_NAME} ORDER BY ${ArticleTableInfo.DATE_COLUMN_NAME}")
    fun observeArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM ${ArticleTableInfo.TABLE_NAME} WHERE ${ArticleTableInfo.PRIMARY_KEY_COLUMN_NAME} = :articleId")
    suspend fun getArticleBy(articleId: Long): ArticleEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articles: List<ArticleEntity>): List<Long>

    @Query("SELECT MAX(${ArticleTableInfo.DATE_COLUMN_NAME}) FROM ${ArticleTableInfo.TABLE_NAME}")
    suspend fun getNewestArticleDate(): Long?

    @Query("DELETE FROM ${ArticleTableInfo.TABLE_NAME} WHERE ${ArticleTableInfo.DATE_COLUMN_NAME} < :dateEpochTimeMilli")
    suspend fun deleteOutdatedArticles(dateEpochTimeMilli: Long): Int
}