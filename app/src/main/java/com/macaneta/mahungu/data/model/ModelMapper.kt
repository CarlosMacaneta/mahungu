package com.macaneta.mahungu.data.model

import com.macaneta.mahungu.data.model.api.Article
import com.macaneta.mahungu.data.model.api.Source
import com.macaneta.mahungu.data.model.entity.ArticleEntity
import com.macaneta.mahungu.data.model.entity.SourceEntity

fun Source.toEntity() = SourceEntity(
    key = id,
    name = name,
    description = description,
    url = url,
    category = category,
    language = language,
    country = country
)

fun Article.toEntity(cacheLabel: String) = ArticleEntity(
    sourceId = source?.toEntity()?.id ?: -1,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    cacheLabel = cacheLabel
)

fun List<Article>.toEntities(cacheLabel: String) = map { it.toEntity(cacheLabel) }