package com.submission.dicodingstory.dbroom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RKDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyModel>)


    @Query("SELECT * FROM remotekey_table WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeyModel?


    @Query("DELETE FROM remotekey_table")
    suspend fun deleteRemoteKeys()

}