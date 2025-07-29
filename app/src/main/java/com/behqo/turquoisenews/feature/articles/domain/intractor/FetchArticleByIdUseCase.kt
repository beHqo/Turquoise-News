package com.behqo.turquoisenews.feature.articles.domain.intractor

import com.behqo.turquoisenews.core.domain.model.Result
import com.behqo.turquoisenews.feature.articles.domain.model.Article
import com.behqo.turquoisenews.feature.articles.domain.repository.IArticlesRepository
import javax.inject.Inject

class FetchArticleByIdUseCase @Inject constructor(private val repo: IArticlesRepository) {
    suspend operator fun invoke(articleId: Long): Result<Article> = repo.getArticleById(articleId)
}