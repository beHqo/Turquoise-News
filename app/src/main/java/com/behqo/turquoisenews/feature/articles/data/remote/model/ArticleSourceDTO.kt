package com.behqo.turquoisenews.feature.articles.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleSourceDTO(val id: String? = null, val name: String = "Unknown Source")
