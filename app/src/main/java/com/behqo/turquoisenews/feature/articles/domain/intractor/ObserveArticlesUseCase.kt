package com.behqo.turquoisenews.feature.articles.domain.intractor

import com.behqo.turquoisenews.core.domain.model.Result
import com.behqo.turquoisenews.feature.articles.domain.model.Article
import com.behqo.turquoisenews.feature.articles.domain.model.SortOrder
import com.behqo.turquoisenews.feature.articles.domain.repository.IArticlesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveArticlesUseCase @Inject constructor(private val repo: IArticlesRepository) {
    operator fun invoke(sortOrder: SortOrder): Flow<Result<List<Article>>> =
        repo.observeArticles(sortOrder)
}