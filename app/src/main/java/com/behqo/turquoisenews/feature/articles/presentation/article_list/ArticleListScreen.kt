package com.behqo.turquoisenews.feature.articles.presentation.article_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.behqo.turquoisenews.R
import com.behqo.turquoisenews.core.domain.model.Error
import com.behqo.turquoisenews.core.domain.model.Result
import com.behqo.turquoisenews.core.presentation.common.LoadingScreen
import com.behqo.turquoisenews.core.presentation.theme.PaddingManager
import com.behqo.turquoisenews.feature.articles.presentation.model.ArticleItem

@Composable
fun ArticleListScreen(
    vm: ArticleListViewModel = hiltViewModel(),
    navigateToArticleDetails: (Long) -> Unit,
    navigateToSettingScreen: () -> Unit,
    showSnackbar: (Error) -> Unit
) {
    val articleListResult by vm.articleListResult.collectAsStateWithLifecycle()

    AnimatedContent(targetState = articleListResult) {
        when (it) {
            is Result.Loading -> LoadingScreen()
            is Result.Failure -> showSnackbar(it.error)
            is Result.Success -> ArticleListScreen(
                articles = it.data,
                onItemClick = navigateToArticleDetails,
                navigateToSettingScreen = navigateToSettingScreen
            )
        }
    }
}

@Composable
private fun ArticleListScreen(
    articles: List<ArticleItem>,
    onItemClick: (Long) -> Unit,
    navigateToSettingScreen: () -> Unit
) = LazyColumn(Modifier.fillMaxSize()) {
    stickyHeader(contentType = { "Header" }) { ArticlesTopBar(navigateToSettingScreen) }

    items(articles, key = { it.articleId }, contentType = { "Item" }) { article ->
        ArticleComponent(article = article) { onItemClick(article.articleId) }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticlesTopBar(
    navigateToSettingScreen: () -> Unit
) = TopAppBar(title = { Text(text = stringResource(R.string.articles_top_bar_title)) }, actions = {
    IconButton(onClick = navigateToSettingScreen) {
        Icon(
            painter = painterResource(id = R.drawable.round_settings_24),
            contentDescription = stringResource(R.string.articles_top_bar_icon_desc)
        )
    }
})

@Composable
private fun ArticleComponent(article: ArticleItem, onClick: () -> Unit) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(PaddingManager.extraLarge), verticalAlignment = Alignment.CenterVertically
) {
    AsyncImage(
        model = article.imageUrl,
        contentScale = ContentScale.Fit,
        contentDescription = article.title,
        error = painterResource(R.drawable.ic_launcher_foreground),
        modifier = Modifier
            .size(64.dp)
            .clip(MaterialTheme.shapes.small)
    )

    Spacer(modifier = Modifier.width(PaddingManager.extraLarge))

    Column(modifier = Modifier.weight(1f)) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(PaddingManager.small))

        Text(
            text = article.description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(PaddingManager.small))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = article.queryTarget.rawValue, style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.width(PaddingManager.large))

            Text(
                text = article.date, style = MaterialTheme.typography.labelSmall
            )
        }
    }
}