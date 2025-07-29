package com.behqo.turquoisenews.core.data.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.CoroutineDispatcher

object HttpEngineFactory {
    fun create(ioDispatcher: CoroutineDispatcher): HttpClientEngine =
        Android.create { dispatcher = ioDispatcher }
}