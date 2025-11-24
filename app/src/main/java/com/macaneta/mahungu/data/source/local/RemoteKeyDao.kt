package com.macaneta.mahungu.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.macaneta.mahungu.data.model.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Upsert
    suspend fun insert(key: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE label = :label")
    suspend fun remoteKeyByLabel(label: String): RemoteKey?

    @Query("DELETE FROM remote_keys WHERE label = :label")
    suspend fun deleteByLabel(label: String)
}