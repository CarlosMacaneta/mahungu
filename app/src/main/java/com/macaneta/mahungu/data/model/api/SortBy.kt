package com.macaneta.mahungu.data.model.api

enum class SortBy(val order: String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt"),
}