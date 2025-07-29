package com.behqo.turquoisenews.feature.articles.di

import com.behqo.turquoisenews.feature.articles.data.repository.ArticlesRepository
import com.behqo.turquoisenews.feature.articles.domain.intractor.IFormatDateTimeUseCase
import com.behqo.turquoisenews.feature.articles.domain.repository.IArticlesRepository
import com.behqo.turquoisenews.feature.articles.framework.interactor.FormatDateTimeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ArticlesModule {
    @Binds
    @Singleton
    fun bindArticlesRepository(articlesRepository: ArticlesRepository): IArticlesRepository

    @Binds //technically, it should be placed in the ViewModelComponent
    fun bindFormatDateTimeUseCase(formatDateTimeUseCase: FormatDateTimeUseCase): IFormatDateTimeUseCase
}