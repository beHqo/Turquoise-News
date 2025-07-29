package com.behqo.turquoisenews.feature.articles.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.behqo.turquoisenews.feature.articles.domain.model.Article
import com.behqo.turquoisenews.feature.articles.domain.model.QueryTarget

object ArticleTableInfo {
    const val TABLE_NAME = "article"
    const val PRIMARY_KEY_COLUMN_NAME = "article_id"
    const val QUERY_TARGET_COLUMN_NAME = "query_target"
    const val SOURCE_ID_COLUMN_NAME = "source_id"
    const val SOURCE_NAME_COLUMN_NAME = "source_name"
    const val AUTHOR_COLUMN_NAME = "author"
    const val TITLE_COLUMN_NAME = "title"
    const val DESCRIPTION_COLUMN_NAME = "description"
    const val URL_COLUMN_NAME = "url"
    const val IMAGE_URL_COLUMN_NAME = "image_url"
    const val DATE_COLUMN_NAME = "date"
    const val CONTENT_COLUMN_NAME = "content"
}

@Entity(tableName = ArticleTableInfo.TABLE_NAME)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ArticleTableInfo.PRIMARY_KEY_COLUMN_NAME) val articleId: Long = 0,
    @ColumnInfo(name = ArticleTableInfo.QUERY_TARGET_COLUMN_NAME) val queryTarget: QueryTarget,
    @ColumnInfo(name = ArticleTableInfo.SOURCE_ID_COLUMN_NAME) val sourceId: String?,
    @ColumnInfo(name = ArticleTableInfo.SOURCE_NAME_COLUMN_NAME) val sourceName: String,
    @ColumnInfo(name = ArticleTableInfo.AUTHOR_COLUMN_NAME) val author: String,
    @ColumnInfo(name = ArticleTableInfo.TITLE_COLUMN_NAME) val title: String,
    @ColumnInfo(name = ArticleTableInfo.DESCRIPTION_COLUMN_NAME) val description: String,
    @ColumnInfo(name = ArticleTableInfo.URL_COLUMN_NAME) val url: String?,
    @ColumnInfo(name = ArticleTableInfo.IMAGE_URL_COLUMN_NAME) val imageUrl: String?,
    @ColumnInfo(
        name = ArticleTableInfo.DATE_COLUMN_NAME,
        index = true
    ) val dateEpochTimeMilli: Long,
    @ColumnInfo(name = ArticleTableInfo.CONTENT_COLUMN_NAME) val content: String
) {
    fun toDomainModel() = Article(
        queryTarget = queryTarget,
        articleId = articleId,
        sourceId = sourceId,
        sourceName = sourceName,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        dateEpochTimeMilli = dateEpochTimeMilli,
        content = content
    )
}