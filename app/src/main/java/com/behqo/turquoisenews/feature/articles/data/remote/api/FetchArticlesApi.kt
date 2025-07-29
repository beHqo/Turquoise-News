package com.behqo.turquoisenews.feature.articles.data.remote.api

import android.util.Log
import com.behqo.turquoisenews.core.data.remote.makeHttpRequestSafely
import com.behqo.turquoisenews.core.di.IoDispatcher
import com.behqo.turquoisenews.core.domain.model.Result
import com.behqo.turquoisenews.core.domain.model.transform
import com.behqo.turquoisenews.feature.articles.data.remote.model.ArticleDTO
import com.behqo.turquoisenews.feature.articles.data.remote.model.NewsDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.util.appendAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "FetchArticlesApi"
private const val ENDPOINT = "everything"
private const val QUERY_PARAMETER = "q"
private const val SORT_ORDER_PARAMETER = "sortBy"
private const val FROM_DATE_PARAMETER = "from"

class FetchArticlesApi @Inject constructor(
    private val client: HttpClient, @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchArticles(
        query: String, sortOrder: String, fromDate: String
    ): Result<List<ArticleDTO>> = withContext(ioDispatcher) {
        makeHttpRequestSafely<NewsDTO>(TAG) {
            client.get(ENDPOINT) {
                url.parameters.appendAll(
                    QUERY_PARAMETER to query,
                    SORT_ORDER_PARAMETER to sortOrder,
                    FROM_DATE_PARAMETER to fromDate
                )
            }
        }.transform { newsDTO ->
            Log.d(TAG, "fetchNews: Articles received: ${newsDTO.totalResults}")

            newsDTO.articles.forEach { articleDTO -> Log.d(TAG, "$articleDTO") }

            newsDTO.articles
        }
    }
}