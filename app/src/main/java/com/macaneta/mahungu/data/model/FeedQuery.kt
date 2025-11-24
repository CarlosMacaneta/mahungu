package com.macaneta.mahungu.data.model

import com.macaneta.mahungu.data.model.api.Category
import com.macaneta.mahungu.data.model.api.Country
import com.macaneta.mahungu.data.model.api.Language
import com.macaneta.mahungu.data.model.api.Search
import com.macaneta.mahungu.data.model.api.SortBy


data class FeedQuery(
    val query: String = "",
    val searchIn: Search = Search.TITLE,
    val category: Category? = null,
    val country: Country = Country.ALL,
    val language: Language = Language.EN,
    val sortBy: SortBy = SortBy.PUBLISHED_AT,
    val sources: List<String> = emptyList()
)
