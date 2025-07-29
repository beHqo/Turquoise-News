package com.behqo.turquoisenews.feature.articles.framework.interactor

import com.behqo.turquoisenews.feature.articles.domain.intractor.IFormatDateTimeUseCase
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FormatDateTimeUseCase @Inject constructor() : IFormatDateTimeUseCase {
    /**
     * Formats the given epoch milliseconds into a string like “2025/07/30 ‑ 18:30”
     * using 24‑hour time and zero‑padded year/month/day.
     */
    override operator fun invoke(epochTimeMilli: Long): String {
        val zoneId = ZoneId.systemDefault()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm")

        return Instant.ofEpochMilli(epochTimeMilli).atZone(zoneId).format(formatter)
    }
}