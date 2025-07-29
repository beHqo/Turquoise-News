package com.behqo.turquoisenews.feature.articles.domain.repository

import com.behqo.turquoisenews.core.domain.model.Result
import com.behqo.turquoisenews.feature.articles.domain.model.Article
import com.behqo.turquoisenews.feature.articles.domain.model.SortOrder
import kotlinx.coroutines.flow.Flow

interface IArticlesRepository {
    fun observeArticles(sortOrder: SortOrder): Flow<Result<List<Article>>>
    suspend fun getArticleById(articleId: Long): Result<Article>
}