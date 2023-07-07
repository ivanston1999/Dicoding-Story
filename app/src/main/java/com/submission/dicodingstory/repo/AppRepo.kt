package com.submission.dicodingstory.repo


import com.submission.dicodingstory.data.StoryData
import com.submission.dicodingstory.error.DataResult
import com.submission.dicodingstory.req.AccountReq
import com.submission.dicodingstory.req.SignupReq
import com.submission.dicodingstory.req.StoryReq
import com.submission.dicodingstory.response.AccountResponse
import com.submission.dicodingstory.response.BaseResponse
import com.submission.dicodingstory.response.StoryResponse
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class AppRepo @Inject constructor(private val data: StoryData) {

    fun signUp(req: SignupReq): Flow<DataResult<BaseResponse>> = data.signUp(req)
    fun signIn(req: AccountReq): Flow<DataResult<AccountResponse>> = data.signIn(req)
    fun getStoryLoc(): Flow<DataResult<StoryResponse>> = data.getStoryLoc()
    fun uploadStory(request: StoryReq, fileManager: File): Flow<DataResult<BaseResponse>> = data.uploadStory(request, fileManager)

}