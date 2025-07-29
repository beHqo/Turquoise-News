package com.behqo.turquoisenews.core.data.local

import androidx.room.TypeConverter
import com.behqo.turquoisenews.feature.articles.domain.model.QueryTarget

class QueryTargetTypeConvertor {
    @TypeConverter
    fun fromQueryTarget(queryTarget: QueryTarget?): String? = queryTarget?.rawValue

    @TypeConverter
    fun toQueryTarget(rawValue: String): QueryTarget =
        QueryTarget.entries.first { it.rawValue == rawValue }
}