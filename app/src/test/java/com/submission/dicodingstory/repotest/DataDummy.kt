package com.submission.dicodingstory.repotest
import com.submission.dicodingstory.model.Account
import com.submission.dicodingstory.model.Story
import com.submission.dicodingstory.response.AccountResponse
import com.submission.dicodingstory.response.BaseResponse
import com.submission.dicodingstory.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

object DataDummy {

    fun generateStoryResponse(): StoryResponse
    {
        val error = false
        val message = "Stories fetched successfully"
        val listStory = mutableListOf<Story>()

        for (i in 0 until 10){
            val data = Story(
                id = "story-vVrNlGXRx4zvffBU",
                name =  "ivan",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1685418458564_iEiSUIeY.jpg",
                lat = null,
                lon = null,
                description = "Hello"
            )
            listStory.add(data)
        }
        return StoryResponse(error, message, listStory)
    }

    fun generateUpdateStory(): List<Story> {
        val items = arrayListOf<Story>()

        for (i in 0 until 10){
            val data = Story(
                id = "story-vVrNlGXRx4zvffBU",
                name =  "ivan",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1685418458564_iEiSUIeY.jpg",
                lat = null,
                lon = null,
                description = "Hello"
            )
            items.add(data)
        }

        return items
    }

    fun generateSignInResponse(): AccountResponse {
        val user = Account(
            userId = "user-PBKwbxNuEIAhRVTC",
            name = "ivan",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVBCS3dieE51RUlBaFJWVEMiLCJpYXQiOjE2ODU0MzA4MzJ9.uEa0RfLi68lcLX-P-d7fErbaEDYpjlTxCY2Rcu9xXRU"
        )

        return AccountResponse(
            false,
            "success",
            user
        )
    }

    fun generateSignInRespWRetrofit(): Response<AccountResponse> {
        val user = Account(
            userId = "story-vVrNlGXRx4zvffBU",
            name = "ivan",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVBCS3dieE51RUlBaFJWVEMiLCJpYXQiOjE2ODU0MzA4MzJ9.uEa0RfLi68lcLX-P-d7fErbaEDYpjlTxCY2Rcu9xXRU"
        )

        return Response.success(AccountResponse(
            false,
            "success",
            user
        ))
    }


    fun generateSignUpResponse(): BaseResponse =
        BaseResponse(false, "success")
    fun generateMultipartFile(): MultipartBody.Part =
        MultipartBody.Part.create("text"
            .toRequestBody())
    fun generateReqBody(): RequestBody =
        "text".toRequestBody()
    fun generateUploadStoryResponse(): BaseResponse =
        BaseResponse(false, "success")
    fun generateSignUpError(): BaseResponse =
        BaseResponse(true, "wrong email")
    fun generateSignInError(): BaseResponse =
        BaseResponse(true, "not found")
    fun generateUploadError(): BaseResponse =
        BaseResponse(true, "ERROR")}