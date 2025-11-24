package com.macaneta.mahungu.data.source.remote

import com.macaneta.mahungu.data.model.api.Feed
import com.macaneta.mahungu.data.model.api.Language
import com.macaneta.mahungu.data.model.api.Search
import com.macaneta.mahungu.data.model.api.SortBy
import retrofit2.Response
import retrofit2.http.GET

interface FeedApiService {
    @GET("everything?searchIn={search.searchIn}&sortBy={sortBy.order}&pageSize={pageSize}&page={page}")
    suspend fun getFeed(
        pageSize: Int = 20,
        page: Int = 1,
        query: String? = null,
        search: Search = Search.TITLE,
        language: Language = Language.EN,
        sortBy: SortBy = SortBy.PUBLISHED_AT
    ): Response<Feed?>
}