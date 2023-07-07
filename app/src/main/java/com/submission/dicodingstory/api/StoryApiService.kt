package com.submission.dicodingstory.api

import com.submission.dicodingstory.response.BaseResponse
import com.submission.dicodingstory.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface StoryApiService {

    @GET("stories")
    suspend fun getMoreStory(
        @Query("page") page: Int,
        @Query("size") size: Int)
    :
            StoryResponse

    @GET("stories")
    suspend fun getStoryLoc(
        @Query("location") location: Int)
    :
            Response<StoryResponse>

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part?,
        @Part("lat") lat: Float? = null,
        @Part("lon") lon: Float? = null)
    :
            Response<BaseResponse>

}