package com.behqo.turquoisenews.feature.articles.data.remote.model

import com.behqo.turquoisenews.core.domain.interactor.IDateTimeParser
import com.behqo.turquoisenews.feature.articles.data.local.model.ArticleEntity
import com.behqo.turquoisenews.feature.articles.domain.model.QueryTarget
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDTO(
    val source: ArticleSourceDTO = ArticleSourceDTO(),
    val author: String = "Unknown Author",
    val title: String = "No Title",
    val description: String = "This article does not have any briefing",
    val url: String?,
    @SerialName("urlToImage") val imageUrl: String?,
    @SerialName("publishedAt") val date: String,
    val content: String = "This article literally does not contain any information!"
) {
    fun toDataModel(queryTarget: QueryTarget, dateTimeParser: IDateTimeParser) = ArticleEntity(
        queryTarget = queryTarget,
        sourceId = source.id,
        sourceName = source.name,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        dateEpochTimeMilli = dateTimeParser.parseIso8601ToEpoch(date),
        content = content
    )
}