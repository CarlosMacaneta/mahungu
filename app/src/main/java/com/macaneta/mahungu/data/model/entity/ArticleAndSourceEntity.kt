package com.macaneta.mahungu.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation


data class ArticleAndSourceEntity(
    @Embedded val article: ArticleEntity,
    @Relation(
        parentColumn = "sourceId",
        entityColumn = "id"
    )
    val source: SourceEntity
)
