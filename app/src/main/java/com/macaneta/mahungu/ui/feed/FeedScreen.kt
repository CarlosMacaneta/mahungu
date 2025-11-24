package com.macaneta.mahungu.ui.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.macaneta.mahungu.ui.components.ArticleCard
import com.macaneta.mahungu.ui.components.FilterBottomSheet
import com.macaneta.mahungu.ui.feed.components.FeedTopBar2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val filters by viewModel.filters.collectAsStateWithLifecycle()
    val pagedItems = viewModel.pagingData.collectAsLazyPagingItems()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showFilterSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            FeedTopBar2(
                onFilterClick = { showFilterSheet = true },
                onSearch = { viewModel.onSearch(it) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            PullToRefreshBox(
                isRefreshing = pagedItems.loadState.refresh is LoadState.Loading,
                onRefresh = { viewModel.loadArticles() },
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    pagedItems.loadState.refresh is LoadState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    pagedItems.itemCount > 0 -> {
                        LazyColumn() {
                            items(pagedItems.itemCount) { index ->
                                ArticleCard(pagedItems[index]!!)
                            }
                        }
                    }

                    pagedItems.itemCount == 0 -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Article,
                                    contentDescription = null,
                                    modifier = Modifier.size(120.dp).align(Alignment.Center)
                                )
                                Icon(
                                    imageVector = Icons.Default.Block,
                                    contentDescription = null,
                                    modifier = Modifier.size(85.dp).align(Alignment.Center),
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                            Text(
                                "There are no articles available right now.\nTry searching applying filters and enjoy! 😉",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

            }
        }
    }

    if (showFilterSheet) {
        FilterBottomSheet(
            currentFilters = filters,
            onFiltersChanged = {
                viewModel.updateFilters(it)
                showFilterSheet = false
            },
            onDismiss = { showFilterSheet = false },
            sheetState = sheetState
        )
    }
}