package com.behqo.turquoisenews.feature.articles.domain.model

enum class SortOrder(val rawValue: String) {
    RELEVANCY("relevancy"), POPULARITY("popularity"), NEWEST("publishedAt")
}