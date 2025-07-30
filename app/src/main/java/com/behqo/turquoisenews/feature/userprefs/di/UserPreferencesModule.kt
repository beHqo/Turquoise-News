package com.behqo.turquoisenews.feature.userprefs.di

import com.behqo.turquoisenews.feature.userprefs.data.repository.UserPreferencesRepository
import com.behqo.turquoisenews.feature.userprefs.domain.repository.IUserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UserPreferencesModule {
    @Binds
    @Singleton
    fun bindUserPreferencesRepository(userPreferencesRepository: UserPreferencesRepository): IUserPreferencesRepository
}