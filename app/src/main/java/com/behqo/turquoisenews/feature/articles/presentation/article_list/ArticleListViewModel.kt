package com.behqo.turquoisenews.feature.articles.presentation.article_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behqo.turquoisenews.core.domain.model.Result
import com.behqo.turquoisenews.core.domain.model.transform
import com.behqo.turquoisenews.feature.articles.domain.intractor.IFormatDateTimeUseCase
import com.behqo.turquoisenews.feature.articles.domain.intractor.ObserveArticlesUseCase
import com.behqo.turquoisenews.feature.articles.domain.model.SortOrder
import com.behqo.turquoisenews.feature.articles.presentation.model.toArticleItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel
@Inject constructor(
    observeArticles: ObserveArticlesUseCase, private val formatDateTime: IFormatDateTimeUseCase
) : ViewModel() {
    val articleListResult = observeArticles(SortOrder.NEWEST)
        .map { result -> result.transform { list -> list.map { it.toArticleItem(formatDateTime) } } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Result.Loading)
}