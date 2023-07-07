package com.submission.dicodingstory.dbroom

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.submission.dicodingstory.model.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(data: List<Story>)

    @Query("SELECT * FROM story_table")
    fun getPostList(): PagingSource<Int, Story>

    @Query("DELETE FROM story_table")
    suspend fun deleteAllPost()
}