package com.behqo.turquoisenews.core.framework.interactor

import com.behqo.turquoisenews.core.domain.interactor.IGetYesterdayEpochTimeMilli
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class GetYesterdayEpochTimeMilli @Inject constructor() : IGetYesterdayEpochTimeMilli {
    override operator fun invoke(): Long {
        val yesterdayDate = LocalDate.now(ZoneOffset.UTC).minusDays(1).atStartOfDay()

        return yesterdayDate.toInstant(ZoneOffset.UTC).toEpochMilli()
    }
}