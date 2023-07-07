package com.submission.dicodingstory.data

import com.submission.dicodingstory.api.AccountApiService
import com.submission.dicodingstory.api.StoryApiService
import com.submission.dicodingstory.error.DataResult
import com.submission.dicodingstory.error.NetworkHandler
import com.submission.dicodingstory.error.ResponseHandler
import com.submission.dicodingstory.req.AccountReq
import com.submission.dicodingstory.req.SignupReq
import com.submission.dicodingstory.req.StoryReq
import com.submission.dicodingstory.response.AccountResponse
import com.submission.dicodingstory.response.BaseResponse
import com.submission.dicodingstory.response.StoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class StoryData @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val converter: ResponseHandler,
    private val accountApiService: AccountApiService,
    private val storyApiService: StoryApiService

    ) {

    fun signUp(req: SignupReq): Flow<DataResult<BaseResponse>> = flow {
        emit(DataResult.loading(null))

        val res = networkHandler.enqueue(req, converter::converterBaseError, accountApiService::signUp)
        emit(res)
    }.flowOn(Dispatchers.IO)

    fun signIn(req: AccountReq): Flow<DataResult<AccountResponse>> = flow {
        emit(DataResult.loading(null))

        val res = networkHandler.enqueue(req, converter::converterBaseError, accountApiService::signIn)
        emit(res)
    }.flowOn(Dispatchers.IO)

    fun getStoryLoc(): Flow<DataResult<StoryResponse>> = flow {
        emit(DataResult.loading(null))

        val res = networkHandler.enqueue(1, converter::converterBaseError, storyApiService::getStoryLoc)
        emit(res)
    }.flowOn(Dispatchers.IO)

    fun uploadStory(request: StoryReq, file: File): Flow<DataResult<BaseResponse>> = flow {
        emit(DataResult.loading(null))

        val desc = request.description.toRequestBody("text/plain".toMediaType())
        val imageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, imageFile)

        val res = networkHandler.enqueue(desc, imageMultipart, request.lat, request.lon, converter::converterBaseError, storyApiService::uploadStory)
        emit(res)
        Timber.d("$res")
    }.flowOn(Dispatchers.IO)

}