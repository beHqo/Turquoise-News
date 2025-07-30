package com.behqo.turquoisenews.core.presentation.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.behqo.turquoisenews.R

@Composable
@OptIn(ExperimentalMaterial3Api::class) //TopAppBar
fun DetailsTopBar(title: String, onNavigateUp: () -> Unit) = TopAppBar(title = {
    Text(text = title, maxLines = 1, overflow = TextOverflow.Companion.Ellipsis)
}, navigationIcon = {
    IconButton(onClick = onNavigateUp) {
        Icon(
            painter = painterResource(id = R.drawable.round_arrow_back_24),
            contentDescription = stringResource(R.string.article_details_top_bar_icon_desc)
        )
    }
})