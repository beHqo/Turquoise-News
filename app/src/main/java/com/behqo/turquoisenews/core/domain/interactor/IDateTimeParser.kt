package com.behqo.turquoisenews.core.domain.interactor

interface IDateTimeParser {
    fun parseIso8601ToEpoch(input: String): Long
    fun formatEpochToIso8601(epochMillis: Long): String
}