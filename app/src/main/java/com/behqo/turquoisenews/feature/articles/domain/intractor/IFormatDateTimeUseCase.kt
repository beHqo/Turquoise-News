package com.behqo.turquoisenews.feature.articles.domain.intractor

interface IFormatDateTimeUseCase {
    operator fun invoke(epochTimeMilli: Long): String
}