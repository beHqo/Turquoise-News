package com.behqo.turquoisenews.core.di

import com.behqo.turquoisenews.core.data.remote.HttpClientFactory
import com.behqo.turquoisenews.core.data.remote.HttpEngineFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpEngine(@IoDispatcher ioDispatcher: CoroutineDispatcher): HttpClientEngine =
        HttpEngineFactory.create(ioDispatcher)

    @Provides
    @Singleton
    fun provideHttpClient(engine: HttpClientEngine): HttpClient = HttpClientFactory.create(engine)
}