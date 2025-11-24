package com.macaneta.mahungu.ui.feed

import com.macaneta.mahungu.data.model.FeedQuery
import com.macaneta.mahungu.data.model.entity.ArticleEntity

sealed interface FeedUiState {
    val isLoading: Boolean
    val errorMessages: List<String>
    val query: FeedQuery?

    data class NoFeed(
        override val isLoading: Boolean = false,
        override val errorMessages: List<String> = emptyList(),
        override val query: FeedQuery? = null,
    ) : FeedUiState

    data class HasFeed(
        val articles: List<ArticleEntity> = emptyList(),
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
        override val query: FeedQuery,
    ) : FeedUiState
}