package com.behqo.turquoisenews.core.data.remote

import android.util.Log
import com.behqo.turquoisenews.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val URL = "https://newsapi.org/v2/"
private const val KTOR_CLIENT_TAG = "KTOR"

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient = HttpClient(engine) {
        defaultRequest {
            url(URL)
            contentType(ContentType.Application.Json)
            header("X-Api-Key", BuildConfig.NEWS_API_KEY)
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                coerceInputValues = true
                explicitNulls = false
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(KTOR_CLIENT_TAG, message)
                }
            }
        }

        install(HttpTimeout) {
            connectTimeoutMillis = 40_000
            requestTimeoutMillis = 20_000
        }
    }
}