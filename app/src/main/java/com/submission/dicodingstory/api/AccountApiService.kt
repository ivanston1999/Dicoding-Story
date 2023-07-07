package com.submission.dicodingstory.api

import com.submission.dicodingstory.req.AccountReq
import com.submission.dicodingstory.req.SignupReq
import com.submission.dicodingstory.response.AccountResponse
import com.submission.dicodingstory.response.BaseResponse
import retrofit2.Response
import retrofit2.http.*

interface AccountApiService {


    @POST("login")
    suspend fun signIn(
        @Body request: AccountReq)
    :
            Response
    <AccountResponse>


    @POST("register")
    suspend fun signUp(
        @Body request: SignupReq)
    :
            Response
    <BaseResponse>





}