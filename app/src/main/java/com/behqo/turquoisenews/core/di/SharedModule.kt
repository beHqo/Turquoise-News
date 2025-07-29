package com.behqo.turquoisenews.core.di

import com.behqo.turquoisenews.core.domain.interactor.IDateTimeParser
import com.behqo.turquoisenews.core.domain.interactor.IGetYesterdayEpochTimeMilli
import com.behqo.turquoisenews.core.framework.interactor.GetYesterdayEpochTimeMilli
import com.behqo.turquoisenews.core.framework.interactor.JavaDateTimeParser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SharedModule {
    @Binds
    fun bindDateTimeParser(javaDateTimeParser: JavaDateTimeParser): IDateTimeParser

    @Binds
    fun bindYesterdayEpochTimeMilli(getYesterdayEpochTimeMilli: GetYesterdayEpochTimeMilli): IGetYesterdayEpochTimeMilli
}