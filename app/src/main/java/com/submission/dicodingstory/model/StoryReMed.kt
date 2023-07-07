package com.submission.dicodingstory.model

import com.submission.dicodingstory.api.StoryApiService
import com.submission.dicodingstory.dbroom.AppDb
import com.submission.dicodingstory.dbroom.RemoteKeyModel
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import javax.inject.Inject
import com.submission.dicodingstory.util.Default.STARTPAGE
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState


@OptIn
    (ExperimentalPagingApi::class)
class StoryReMed @Inject constructor
    (
    private val database: AppDb,
    private val api: StoryApiService
)
    : RemoteMediator<Int, Story>()

{

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Story>
    ): MediatorResult


    {val story = when (loadType)
        {
            LoadType.REFRESH ->
            {
                val rk = getPosition(state)
                rk?.nextKey?.minus(1) ?: STARTPAGE
            }
            LoadType.PREPEND -> {
                val rk = getFirstStory(state)
                val pk = rk?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = rk != null
                )
                pk
            }
            LoadType.APPEND -> {
                val rk = getLastStory(state)
                val nk = rk?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = rk != null
                )
                nk
            }
        }

        return try {
            val resp = api.getMoreStory(story, state.config.pageSize)
            val respStory = resp.listStory
            val endPage = resp.listStory.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.rKDao().deleteRemoteKeys()
                    database.storyDao().deleteAllPost()
                }

                val prevKey = if (story == 1) null else story - 1
                val nextKey = if (endPage) null else story + 1
                val keys = respStory.map {
                    RemoteKeyModel(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.rKDao().insertAll(keys)
                database.storyDao().insertPost(respStory)
            }

            MediatorResult.Success(endOfPaginationReached = endPage)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
    private suspend fun getPosition
                (state: PagingState<Int, Story>): RemoteKeyModel? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database
                    .rKDao()
                    .getRemoteKeysId(id)}}
    override suspend fun initialize(): InitializeAction =
        InitializeAction.LAUNCH_INITIAL_REFRESH
    private suspend fun getLastStory
                (state: PagingState<Int, Story>): RemoteKeyModel? =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database
                .rKDao()
                .getRemoteKeysId(data.id)}
    private suspend fun getFirstStory
                (state: PagingState<Int, Story>): RemoteKeyModel? =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database
                .rKDao()
                .getRemoteKeysId(data.id)}


}
