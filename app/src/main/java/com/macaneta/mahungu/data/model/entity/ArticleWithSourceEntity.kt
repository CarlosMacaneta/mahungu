package com.macaneta.mahungu.data.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation


data class ArticleWithSourceEntity(
    @Embedded val article: ArticleEntity,
    @Relation(
        parentColumn = "sourceId",
        entityColumn = "id"
    )
    val source: SourceEntity
)
