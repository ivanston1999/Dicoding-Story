package com.submission.dicodingstory.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.submission.dicodingstory.error.DataResult
import com.submission.dicodingstory.error.OperationStatus
import com.submission.dicodingstory.main.SPageAdapter
import com.submission.dicodingstory.model.Story
import com.submission.dicodingstory.repo.AppRepo
import com.submission.dicodingstory.repo.StoryMedRepo
import com.submission.dicodingstory.repotest.CorouTR
import com.submission.dicodingstory.repotest.DataDummy
import com.submission.dicodingstory.repotest.PageTestStoryData
import com.submission.dicodingstory.repotest.getOrAwaitValue
import com.submission.dicodingstory.req.StoryReq
import com.submission.dicodingstory.response.BaseResponse
import com.submission.dicodingstory.response.StoryResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryVMTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var corouTR = CorouTR()

    @Mock
    private lateinit var appRepo: AppRepo
    @Mock
    private lateinit var storyMedRepo: StoryMedRepo

    private lateinit var viewModel: StoryVM

    @Before
    fun setup() {
        viewModel = StoryVM(appRepo, storyMedRepo)
    }

    @Test
    fun `Get story with location - success`() = runTest {
        val expecResp = DataDummy.generateStoryResponse()
        val dataStory = flowOf(DataResult.success(expecResp))
        `when`(appRepo.getStoryLoc()).thenReturn(dataStory)

        val actual = viewModel.getStoryLoc().getOrAwaitValue()

        verify(appRepo).getStoryLoc()
        advanceUntilIdle()
        assertNotNull(actual)
        assertEquals(actual.data, expecResp)
    }

    @Test
    fun `Get story - success`() = runTest {
        val dataD = DataDummy.generateUpdateStory()
        val dataStory = PageTestStoryData.snapshot(dataD)
        val story = MutableStateFlow<PagingData<Story>>(dataStory)
        `when`(storyMedRepo.getStoryUpdate()).thenReturn(story)

        val realRes = viewModel.getMoreStory().getOrAwaitValue()
        val diff = AsyncPagingDataDiffer(
            diffCallback = SPageAdapter.differCallback,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = corouTR.testDispatcher,
            workerDispatcher = corouTR.testDispatcher
        )
        diff.submitData(realRes)
        advanceUntilIdle()

        verify(storyMedRepo).getStoryUpdate()
        assertNotNull(diff.snapshot())
        assertEquals(dataD.size, diff.snapshot().size)

        val firstItem = diff.snapshot().items.firstOrNull()
        assertNotNull(firstItem)
        assertEquals(dataD.first(), firstItem)
    }


    @Test
    fun `Get story - error`() = runTest {
        val exception = Exception("Failed to fetch story")
        val story = MutableStateFlow<PagingData<Story>>(PagingData.empty())
        `when`(storyMedRepo.getStoryUpdate()).thenReturn(story)

        val realRes = viewModel.getMoreStory().getOrAwaitValue()
        val diff = AsyncPagingDataDiffer(
            diffCallback = SPageAdapter.differCallback,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = corouTR.testDispatcher,
            workerDispatcher = corouTR.testDispatcher
        )
        diff.submitData(realRes)
        advanceUntilIdle()

        verify(storyMedRepo).getStoryUpdate()
        assertNotNull(diff.snapshot())
        assertEquals(0, diff.snapshot().size)
        assertNull(diff.snapshot().items.firstOrNull())

    }










    @Test
    fun `Upload new story - success`() = runTest {
        val expecResp = DataDummy.generateUploadStoryResponse()
        val fileImg = File("image")
        val req = StoryReq("desc", null, null)
        val dataBase: Flow<DataResult<BaseResponse>> = flowOf(DataResult.success(expecResp))
        `when`(appRepo.uploadStory(req, fileImg)).thenReturn(dataBase)

        val actual = viewModel.uploadStory(req, fileImg).getOrAwaitValue()

        verify(appRepo).uploadStory(req, fileImg)
        advanceUntilIdle()

        assertNotNull(actual)
        assertEquals(actual.data, expecResp)
    }




    @Test
    fun `Upload new story - error`() = runTest {
        val fileImg = File("image")
        val req = StoryReq("desc", null, null)
        val dataBase: Flow<DataResult<BaseResponse>> = flowOf(DataResult.error("UNKNOWN ERROR", null))
        `when`(appRepo.uploadStory(req, fileImg)).thenReturn(dataBase)

        val actual = viewModel.uploadStory(req, fileImg).getOrAwaitValue()

        verify(appRepo).uploadStory(req, fileImg)
        advanceUntilIdle()

        assertNotNull(actual)
        assertTrue(actual.status == OperationStatus.ERROR)
    }

    @Test
    fun `Get story with location - error`() = runTest {
        val dataStory: Flow<DataResult<StoryResponse>> = flowOf(DataResult.error("UNKNOWN ERROR", null))
        `when`(appRepo.getStoryLoc()).thenReturn(dataStory)

        val real = viewModel.getStoryLoc().getOrAwaitValue()

        verify(appRepo).getStoryLoc()
        advanceUntilIdle()
        assertNotNull(real)
        assertTrue(real.status == OperationStatus.ERROR)
    }


    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}
