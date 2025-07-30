package com.behqo.turquoisenews.feature.articles.presentation.article_details

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.behqo.turquoisenews.R
import com.behqo.turquoisenews.core.domain.model.Error
import com.behqo.turquoisenews.core.domain.model.Result
import com.behqo.turquoisenews.core.presentation.common.DetailsTopBar
import com.behqo.turquoisenews.core.presentation.common.LoadingScreen
import com.behqo.turquoisenews.core.presentation.theme.PaddingManager
import com.behqo.turquoisenews.feature.articles.presentation.model.ArticleDetailsItem

@Composable
fun ArticleDetailsScreen(
    vm: ArticleDetailsViewModel = hiltViewModel(),
    showSnackbar: (Error) -> Unit,
    navigateUp: () -> Unit
) {
    val articleDetailsResult by vm.articleDetailsResult.collectAsStateWithLifecycle()

    AnimatedContent(targetState = articleDetailsResult) {
        when (it) {
            is Result.Loading -> LoadingScreen()
            is Result.Failure -> showSnackbar(it.error)
            is Result.Success -> ArticleDetailsScreen(it.data, navigateUp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleDetailsScreen(
    articleDetails: ArticleDetailsItem, onNavigateUp: () -> Unit
) = Column(modifier = Modifier.fillMaxSize()) {
    val context = LocalContext.current

    DetailsTopBar(articleDetails.title, onNavigateUp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingManager.extraLarge)
    ) {
        AsyncImage(
            model = articleDetails.imageUrl,
            contentDescription = articleDetails.title,
            contentScale = ContentScale.Fit,
            error = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.small)
        )

        Spacer(modifier = Modifier.height(PaddingManager.extraLarge))

        Text(
            text = articleDetails.sourceName,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.clickable {
                articleDetails.url?.toUri()?.let { uri ->
                    val intent = Intent(Intent.ACTION_VIEW, uri)

                    context.startActivity(intent)
                }
            })

        Spacer(modifier = Modifier.height(PaddingManager.small))

        Text(
            text = "By ${articleDetails.author}", style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(PaddingManager.small))

        Text(
            text = articleDetails.date, style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(PaddingManager.extraLarge))

        Text(
            text = articleDetails.content, style = MaterialTheme.typography.bodyMedium
        )
    }
}

// -------- Previews --------
val sampleDetails = ArticleDetailsItem(
    articleId = 1L,
    sourceName = "Boredpanda.com",
    author = "Justinas Keturka",
    title = "Wildest NYC Conversations People Can't Believe",
    content = "Most cities are nouns. New York is a verb. This quote is often attributed to JFK, but even if he didn't say it, it still rings true today...",
    imageUrl = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
    url = "https://google.com",
    date = "2025-07-28T07:10:09Z"
)

@Preview(showBackground = true)
@Composable
fun ArticleDetailsScreenPreview() {
    ArticleDetailsScreen(
        articleDetails = sampleDetails, onNavigateUp = {})
}