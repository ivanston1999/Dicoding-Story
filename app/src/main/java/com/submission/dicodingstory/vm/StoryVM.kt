package com.submission.dicodingstory.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.submission.dicodingstory.error.DataResult
import com.submission.dicodingstory.model.Story
import com.submission.dicodingstory.repo.AppRepo
import com.submission.dicodingstory.repo.StoryMedRepo
import com.submission.dicodingstory.req.StoryReq
import com.submission.dicodingstory.response.BaseResponse
import com.submission.dicodingstory.response.StoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StoryVM @Inject constructor(
    private val repo: AppRepo,
    private val repoMediator: StoryMedRepo

) : ViewModel() {

    fun getMoreStory(): LiveData<PagingData<Story>> =
        repoMediator.getStoryUpdate().asLiveData().cachedIn(viewModelScope)

    fun getStoryLoc(): LiveData<DataResult<StoryResponse>> =
        repo.getStoryLoc().asLiveData()

    fun uploadStory(request: StoryReq, file: File): LiveData<DataResult<BaseResponse>> =
        repo.uploadStory(request, file).asLiveData()

    }