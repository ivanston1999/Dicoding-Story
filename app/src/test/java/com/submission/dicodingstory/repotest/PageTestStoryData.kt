package com.submission.dicodingstory.repotest
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.submission.dicodingstory.model.Story

class PageTestStoryData : PagingSource<Int, LiveData<List<Story>>>() {

    companion object{
        fun snapshot(items: List<Story>): PagingData<Story> =
            PagingData.from(items)
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> =
        LoadResult.Page(emptyList(), 0, 1)
}