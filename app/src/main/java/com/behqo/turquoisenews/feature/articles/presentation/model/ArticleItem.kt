package com.behqo.turquoisenews.feature.articles.presentation.model

import com.behqo.turquoisenews.feature.articles.domain.intractor.IFormatDateTimeUseCase
import com.behqo.turquoisenews.feature.articles.domain.model.Article
import com.behqo.turquoisenews.feature.articles.domain.model.QueryTarget

data class ArticleItem(
    val articleId: Long,
    val queryTarget: QueryTarget,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val date: String
)

fun Article.toArticleItem(formatDateTime: IFormatDateTimeUseCase) = ArticleItem(
    queryTarget = queryTarget,
    articleId = articleId,
    title = title,
    description = description,
    imageUrl = imageUrl,
    date = formatDateTime(dateEpochTimeMilli)
)