package com.behqo.turquoisenews.feature.articles.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsDTO(val status: String, val totalResults: Int, val articles: List<ArticleDTO>)