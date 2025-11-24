package com.macaneta.mahungu.data.model.api

import com.macaneta.mahungu.data.model.error.Status
import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    val status: String = Status.OK.status,
    val code: String? = null,
    val message: String? = null,
    val totalResults: Int = -1,
    val articles: List<Article> = emptyList(),
)
