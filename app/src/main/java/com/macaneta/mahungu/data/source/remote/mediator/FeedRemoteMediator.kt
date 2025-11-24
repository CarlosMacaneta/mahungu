package com.macaneta.mahungu.data.source.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.macaneta.mahungu.data.model.FeedQuery
import com.macaneta.mahungu.data.model.entity.ArticleEntity
import com.macaneta.mahungu.data.model.entity.RemoteKey
import com.macaneta.mahungu.data.model.error.ApiError
import com.macaneta.mahungu.data.model.error.Status
import com.macaneta.mahungu.data.model.toEntities
import com.macaneta.mahungu.data.source.local.AppDatabase
import com.macaneta.mahungu.data.source.remote.FeedApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator @Inject constructor(
    private val feedApiService: FeedApiService,
    private val appDb: AppDatabase,
    private val feedQuery: FeedQuery
) : RemoteMediator<Int, ArticleEntity>() {

    companion object {
        const val INITIAL_PAGE = 1
    }

    private val remoteKeyLabel = "feed_${feedQuery.hashCode()}"

    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ) =
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosedToCurrentPosition(state)

                    remoteKey?.nextKey?.minus(1) ?: INITIAL_PAGE
                }

                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)

                    remoteKey?.prevKey ?: return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)

                    remoteKey?.nextKey ?: return MediatorResult.Success(true)
                }
            }

            val response = feedApiService.getFeed(
                query = feedQuery.query,
                search = feedQuery.searchIn,
                language = feedQuery.language,
                sortBy = feedQuery.sortBy,
                pageSize = state.config.pageSize,
                page = page,
            )

            if (!response.isSuccessful) MediatorResult.Error(
                ApiError(
                    code = response.code().toString(),
                    message = response.message()
                )
            )

            val feed = response.body()

            if (feed?.status != Status.OK.status) {
                MediatorResult.Error(
                    ApiError(
                        code = feed?.code.orEmpty(),
                        message = feed?.message.orEmpty()
                    )
                )
            }

            val articles = feed?.articles.orEmpty()
            val endOfPagination = articles.size < state.config.pageSize

            appDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDb.remoteKeyDao().deleteByLabel(remoteKeyLabel)
                    appDb.articleDao().deleteByLabel(remoteKeyLabel)
                }

                appDb.articleDao().insertAll(articles.toEntities(remoteKeyLabel))

                val prevKey = if (page == INITIAL_PAGE) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1
                appDb.remoteKeyDao().insert(RemoteKey(remoteKeyLabel, prevKey, nextKey))

                MediatorResult.Success(endOfPagination)
            }

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                appDb.remoteKeyDao().remoteKeyByLabel(it.cacheLabel)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleEntity>): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                appDb.remoteKeyDao().remoteKeyByLabel(it.cacheLabel)
            }
    }

    private suspend fun getRemoteKeyClosedToCurrentPosition(state: PagingState<Int, ArticleEntity>): RemoteKey? {
        return state.anchorPosition?.let { pos ->
            state.closestItemToPosition(pos)?.cacheLabel?.let { label ->
                appDb.remoteKeyDao().remoteKeyByLabel(label)
            }
        }
    }
}