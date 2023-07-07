package com.submission.dicodingstory.repotest

import com.submission.dicodingstory.api.AccountApiService
import com.submission.dicodingstory.api.StoryApiService
import com.submission.dicodingstory.req.AccountReq
import com.submission.dicodingstory.req.SignupReq
import com.submission.dicodingstory.response.AccountResponse
import com.submission.dicodingstory.response.BaseResponse
import com.submission.dicodingstory.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class DummyServ : AccountApiService, StoryApiService {

    override suspend fun getMoreStory(page: Int, size: Int): StoryResponse =
        DataDummy.generateStoryResponse()

    override suspend fun getStoryLoc(location: Int): Response<StoryResponse> =
        Response.success(DataDummy.generateStoryResponse())

    override suspend fun uploadStory(
        description: RequestBody,
        file: MultipartBody.Part?,
        lat: Float?,
        lon: Float?
    ): Response<BaseResponse> =
        Response.success(DataDummy.generateUploadStoryResponse())

    override suspend fun signUp(request: SignupReq): Response<BaseResponse> =
        Response.success(DataDummy.generateSignUpResponse())

    override suspend fun signIn(request: AccountReq): Response<AccountResponse> =
        Response.success(DataDummy.generateSignInResponse())

    fun signUpError(): BaseResponse =
        DataDummy.generateSignUpError()

    fun signInError(): BaseResponse =
        DataDummy.generateSignInError()

    fun uploadError(): BaseResponse =
        DataDummy.generateUploadError()
}