package com.behqo.turquoisenews.feature.articles.data.repository

import android.util.Log
import com.behqo.turquoisenews.core.di.DefaultDispatcher
import com.behqo.turquoisenews.core.di.IoDispatcher
import com.behqo.turquoisenews.core.domain.interactor.IDateTimeParser
import com.behqo.turquoisenews.core.domain.interactor.IGetYesterdayEpochTimeMilli
import com.behqo.turquoisenews.core.domain.model.DatabaseError
import com.behqo.turquoisenews.core.domain.model.Result
import com.behqo.turquoisenews.feature.articles.data.local.dao.ArticlesDao
import com.behqo.turquoisenews.feature.articles.data.local.model.ArticleEntity
import com.behqo.turquoisenews.feature.articles.data.remote.api.FetchArticlesApi
import com.behqo.turquoisenews.feature.articles.domain.intractor.RoundRobinUseCase
import com.behqo.turquoisenews.feature.articles.domain.model.Article
import com.behqo.turquoisenews.feature.articles.domain.model.QueryTarget
import com.behqo.turquoisenews.feature.articles.domain.model.SortOrder
import com.behqo.turquoisenews.feature.articles.domain.repository.IArticlesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.max

private const val TAG = "ArticlesRepository"

class ArticlesRepository @Inject constructor(
    private val api: FetchArticlesApi,
    private val dao: ArticlesDao,
    private val getYesterdayEpochTimeMilli: IGetYesterdayEpochTimeMilli,
    private val dateTimeParser: IDateTimeParser,
    private val roundRobin: RoundRobinUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : IArticlesRepository {
    override fun observeArticles(sortOrder: SortOrder): Flow<Result<List<Article>>> =
        dao.observeArticles().map { list -> mapToResult(list) }.onStart { refreshCache(sortOrder) }

    private suspend fun mapToResult(
        list: List<ArticleEntity>
    ): Result<List<Article>> = withContext(defaultDispatcher) {
        Result.Success(list.map { entity -> entity.toDomainModel() })
    }

    private suspend fun FlowCollector<Result<List<Article>>>.refreshCache(sortOrder: SortOrder) {
        emit(Result.Loading)

        val yesterdayEpochTimeMilli = getYesterdayEpochTimeMilli()

        dao.deleteOutdatedArticles(yesterdayEpochTimeMilli)

        // One milli-second after the newest article that has been cached, so we would not get duplicates
        val latestCachedArticleTimeMilli = (dao.getNewestArticleDate() ?: -1L) + 1

        // Get articles from either yesterday, or from the last time our local database was updated
        val fromDate = dateTimeParser.formatEpochToIso8601(
            max(yesterdayEpochTimeMilli, latestCachedArticleTimeMilli)
        )

        val buckets = mutableListOf<List<ArticleEntity>>()
        for (query in QueryTarget.entries) {
            val result = api.fetchArticles(
                query = query.rawValue, sortOrder = sortOrder.rawValue, fromDate = fromDate
            )

            if (result is Result.Failure) emit(result)
            else if (result is Result.Success) {
                val articleEntities = result.data.map { it.toDataModel(query, dateTimeParser) }

                buckets += articleEntities
            }
        }

        if (buckets.all { list -> list.isEmpty() }) {
            Log.d(TAG, "refreshCache: Local database is already up-to-date")
            return
        }

        val sequencedArticleEntities = roundRobin(buckets) { it.url }

        insertAll(sequencedArticleEntities)
    }

    private suspend fun insertAll(articles: List<ArticleEntity>) = withContext(ioDispatcher) {
        if (articles.isEmpty()) {
            Log.d(TAG, "insertAll: article list was empty. Not inserting anything.")
            return@withContext
        }

        val idList = dao.insertAll(articles)

        if (idList.contains(-1)) Log.e(TAG, "insertAll: Failed to cache some of the articles")
        if (idList.isEmpty()) Log.e(TAG, "insertAll: Failed to cache any articles")
    }

    override suspend fun getArticleById(articleId: Long): Result<Article> {
        val articleEntity = dao.getArticleBy(articleId)

        if (articleEntity == null) {
            Log.e(
                TAG, "getArticleById: Could not find any articles with the given id of $articleId"
            )
            return Result.Failure(DatabaseError.NO_SUCH_ELEMENT)
        }

        return withContext(defaultDispatcher) { Result.Success(articleEntity.toDomainModel()) }
    }
}

