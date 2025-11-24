package com.macaneta.mahungu.data.source.remote.repository

import androidx.paging.PagingData
import com.macaneta.mahungu.data.model.FeedQuery
import com.macaneta.mahungu.data.model.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getArticlesStream(query: FeedQuery): Flow<PagingData<ArticleEntity>>
}