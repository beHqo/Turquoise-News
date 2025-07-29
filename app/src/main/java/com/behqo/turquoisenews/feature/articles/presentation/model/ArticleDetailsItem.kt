package com.behqo.turquoisenews.feature.articles.presentation.model

import com.behqo.turquoisenews.feature.articles.domain.intractor.IFormatDateTimeUseCase
import com.behqo.turquoisenews.feature.articles.domain.model.Article

data class ArticleDetailsItem(
    val articleId: Long,
    val sourceName: String,
    val author: String,
    val title: String,
    val content: String,
    val url: String?,
    val imageUrl: String?,
    val date: String,
)

fun Article.toArticleDetails(formatDateTime: IFormatDateTimeUseCase) = ArticleDetailsItem(
    articleId = articleId,
    sourceName = sourceName,
    author = author,
    title = title,
    url = url,
    imageUrl = imageUrl,
    date = formatDateTime(dateEpochTimeMilli),
    content = content
)