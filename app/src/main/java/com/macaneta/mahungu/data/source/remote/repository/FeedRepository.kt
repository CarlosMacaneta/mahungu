package com.macaneta.mahungu.data.source.remote.repository

import com.macaneta.mahungu.data.model.api.Feed
import com.macaneta.mahungu.data.source.Result

interface FeedRepository {
    suspend fun fetchTopHeadlines(): Result<Feed>
    suspend fun fetchArticles(): Result<Feed?>
    suspend fun searchArticles(query: String): Result<Feed>
}