package com.macaneta.mahungu.data.source.remote.repository.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.macaneta.mahungu.data.model.FeedQuery
import com.macaneta.mahungu.data.model.entity.ArticleEntity
import com.macaneta.mahungu.data.source.local.AppDatabase
import com.macaneta.mahungu.data.source.remote.FeedApiService
import com.macaneta.mahungu.data.source.remote.mediator.FeedRemoteMediator
import com.macaneta.mahungu.data.source.remote.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val appDb: AppDatabase,
    private val feedApiService: FeedApiService
) : FeedRepository {

    private val mediators = mutableMapOf<FeedQuery, FeedRemoteMediator>()

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticlesStream(query: FeedQuery): Flow<PagingData<ArticleEntity>> {
        val label  = "feed_${query.hashCode()}"

        val pagingSourceFactory = {
            appDb.articleDao().pagingSource(label)
        }

        val mediator = mediators.getOrPut(query) {
            FeedRemoteMediator(feedApiService, appDb, query)
        }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            remoteMediator = mediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}