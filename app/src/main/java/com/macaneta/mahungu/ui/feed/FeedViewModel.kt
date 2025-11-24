package com.macaneta.mahungu.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.macaneta.mahungu.data.model.FeedQuery
import com.macaneta.mahungu.data.model.entity.ArticleEntity
import com.macaneta.mahungu.data.source.remote.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState.NoFeed())
    val state: StateFlow<FeedUiState> = _uiState

    private val _filters = MutableStateFlow(FeedQuery())
    val filters: StateFlow<FeedQuery> = _filters.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingData: Flow<PagingData<ArticleEntity>> = _filters
        .flatMapLatest { newQuery ->
            repository.getArticlesStream(newQuery)
        }
        .cachedIn(viewModelScope)

    init {
        loadArticles()
    }

    fun onSearch(query: String) {
        _filters.update { it.copy(query = query) }
        loadArticles()
    }

    fun updateFilters(newFilters: FeedQuery) {
        _filters.value = newFilters
        loadArticles()
    }

    fun loadArticles() {
        viewModelScope.launch {

        }
    }
}
