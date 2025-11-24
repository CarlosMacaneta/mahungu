package com.macaneta.mahungu.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.macaneta.mahungu.data.model.entity.ArticleEntity
import com.macaneta.mahungu.data.model.entity.ArticleWithSourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Upsert
    suspend fun insert(article: ArticleEntity)

    @Upsert
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAll(): Flow<List<ArticleEntity>>

    @Transaction
    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAllWithSource(): Flow<List<ArticleWithSourceEntity>>

    @Query("DELETE FROM articles")
    suspend fun deleteAll()

    @Query("DELETE FROM articles WHERE cacheLabel = :label")
    suspend fun deleteByLabel(label: String)

}