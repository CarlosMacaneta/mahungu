package com.macaneta.mahungu.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.macaneta.mahungu.data.model.entity.ArticleEntity
import com.macaneta.mahungu.data.model.entity.RemoteKey
import com.macaneta.mahungu.data.model.entity.SourceEntity

@Database(
    entities = [
        ArticleEntity::class,
        SourceEntity::class,
        RemoteKey::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {



    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeyDao(): RemoteKeyDao

}