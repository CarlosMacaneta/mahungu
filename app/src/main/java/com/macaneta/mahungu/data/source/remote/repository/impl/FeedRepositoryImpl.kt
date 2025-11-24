package com.macaneta.mahungu.data.source.remote.repository.impl

import com.macaneta.mahungu.data.model.api.Feed
import com.macaneta.mahungu.data.model.error.ApiError
import com.macaneta.mahungu.data.model.error.Status
import com.macaneta.mahungu.data.source.Result
import com.macaneta.mahungu.data.source.remote.FeedApiService
import com.macaneta.mahungu.data.source.remote.repository.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedApiService: FeedApiService
) : FeedRepository {
    override suspend fun fetchTopHeadlines(): Result<Feed> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchArticles(): Result<Feed?> {
        val response = feedApiService.getFeed()

        if (response.isSuccessful.not()) {
            return Result.Error(
                ApiError(
                    Status.ERROR.status,
                    response.code().toString(),
                    response.message()
                )
            )
        }

        val feed = response.body()

        return when (feed?.status) {
            Status.OK.status -> Result.Success(feed)
            Status.ERROR.status -> {
                Result.Error(
                    ApiError(
                        Status.ERROR.status,
                        response.code().toString(),
                        response.message()
                    )
                )
            }
            else -> Result.Error(Exception("Unknown error"))
        }
    }

    override suspend fun searchArticles(query: String): Result<Feed> {
        TODO("Not yet implemented")
    }
}