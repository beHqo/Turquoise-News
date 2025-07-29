package com.behqo.turquoisenews.feature.articles.domain.model

data class Article(
    val articleId: Long,
    val queryTarget: QueryTarget,
    val sourceId: String?,
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String?,
    val imageUrl: String?,
    val dateEpochTimeMilli: Long,
    val content: String
)