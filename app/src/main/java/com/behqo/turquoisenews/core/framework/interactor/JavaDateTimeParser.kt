package com.behqo.turquoisenews.core.framework.interactor

import com.behqo.turquoisenews.core.domain.interactor.IDateTimeParser
import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class JavaDateTimeParser @Inject constructor() : IDateTimeParser {
    private val parser = DateTimeFormatter.ISO_INSTANT

    override fun parseIso8601ToEpoch(input: String): Long =
        Instant.from(parser.parse(input)).toEpochMilli()

    override fun formatEpochToIso8601(epochMillis: Long): String =
        Instant.ofEpochMilli(epochMillis).toString()
}