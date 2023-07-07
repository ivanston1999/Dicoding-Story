package com.submission.dicodingstory.repotest

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.ListUpdateCallback
import com.submission.dicodingstory.main.SPageAdapter
import com.submission.dicodingstory.repo.StoryMedRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryMedRepoTest
{
    @get:Rule
    var corouTR = CorouTR()
    @Mock
    private lateinit var repo: StoryMedRepo
    @Test
    fun `Gate paged story - success`() = runTest {
        val dummyStoryData = DataDummy.generateUpdateStory()
        val dataPage = PageTestStoryData.snapshot(dummyStoryData)
        val expected = flowOf(dataPage)
        val response = DataDummy.generateStoryResponse()

        `when`(repo.getStoryUpdate()).thenReturn(expected)
        repo.getStoryUpdate().collect{actual ->
            val differ = AsyncPagingDataDiffer(
                diffCallback = SPageAdapter.differCallback,
                updateCallback = noopListUpdateCallback,
                mainDispatcher = corouTR.testDispatcher,
                workerDispatcher = corouTR.testDispatcher
            )
            differ
                .submitData(actual)

            Assert
                .assertNotNull(differ.snapshot())
            Assert
                .assertEquals(response.listStory.size, differ.snapshot().size)}}
    private val noopListUpdateCallback = object : ListUpdateCallback
    {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}}}
