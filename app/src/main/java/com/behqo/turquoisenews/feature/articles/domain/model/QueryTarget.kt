package com.behqo.turquoisenews.feature.articles.domain.model

// The order of entries matter, per the project's requirements
enum class QueryTarget(val rawValue: String) {
    MICROSOFT("microsoft"), APPLE("apple"), GOOGLE("google"), TESLA("tesla")
}