package com.macaneta.mahungu.data.model

import com.macaneta.mahungu.data.model.api.Language
import com.macaneta.mahungu.data.model.api.Search
import com.macaneta.mahungu.data.model.api.SortBy


data class FeedQuery(
    val query: String = "",
    val searchIn: Search = Search.TITLE,
    val language: Language = Language.EN,
    val sortBy: SortBy = SortBy.PUBLISHED_AT,
    val sources: List<String> = emptyList()
)
