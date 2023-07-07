package com.submission.dicodingstory.dbroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remotekey_table")
data class  RemoteKeyModel(
    @PrimaryKey
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)