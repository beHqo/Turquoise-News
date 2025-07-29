package com.behqo.turquoisenews.feature.articles.presentation.article_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behqo.turquoisenews.core.domain.model.DatabaseError
import com.behqo.turquoisenews.core.domain.model.Result
import com.behqo.turquoisenews.core.domain.model.transform
import com.behqo.turquoisenews.feature.articles.domain.intractor.FetchArticleByIdUseCase
import com.behqo.turquoisenews.feature.articles.domain.intractor.IFormatDateTimeUseCase
import com.behqo.turquoisenews.feature.articles.presentation.model.ArticleDetailsItem
import com.behqo.turquoisenews.feature.articles.presentation.model.toArticleDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchArticleById: FetchArticleByIdUseCase,
    private val formatDateTime: IFormatDateTimeUseCase
) : ViewModel() {
    val articleId = savedStateHandle["articleId"] ?: -1L

    private val _articleDetailsResult = MutableStateFlow<Result<ArticleDetailsItem>>(Result.Loading)
    val articleDetailsResult = _articleDetailsResult
        .asStateFlow()
        .onStart { fetchSafely() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Result.Loading)

    private fun fetchSafely() {
        if (articleId == -1L) _articleDetailsResult.update { Result.Failure(DatabaseError.UNKNOWN_IO) }
        else viewModelScope.launch {
            _articleDetailsResult.update {
                fetchArticleById(articleId).transform { it.toArticleDetails(formatDateTime) }
            }
        }
    }
}