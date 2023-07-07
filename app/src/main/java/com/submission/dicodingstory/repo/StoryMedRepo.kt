package com.submission.dicodingstory.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.submission.dicodingstory.api.StoryApiService
import com.submission.dicodingstory.dbroom.AppDb
import com.submission.dicodingstory.model.Story
import com.submission.dicodingstory.model.StoryReMed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryMedRepo @Inject constructor(
    private val db: AppDb,
    private val service: StoryApiService)
{ @OptIn(ExperimentalPagingApi::class)

    fun getStoryUpdate(): Flow<PagingData<Story>> = Pager(
        config = PagingConfig

            (pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false)
        ,remoteMediator = StoryReMed(db, service)
        ,pagingSourceFactory = {db.storyDao().getPostList()}
    )
        .flow}