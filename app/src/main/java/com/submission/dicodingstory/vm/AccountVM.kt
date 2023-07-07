package com.submission.dicodingstory.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.dicodingstory.error.DataResult
import com.submission.dicodingstory.repo.AppRepo
import com.submission.dicodingstory.req.AccountReq
import com.submission.dicodingstory.req.SignupReq
import com.submission.dicodingstory.response.AccountResponse
import com.submission.dicodingstory.response.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountVM @Inject constructor(private val repo: AppRepo) : ViewModel() {


    fun signUp(req: SignupReq): LiveData<DataResult<BaseResponse>> = repo.signUp(req).asLiveData()




    fun signIn(req: AccountReq): LiveData<DataResult<AccountResponse>> = repo.signIn(req).asLiveData()}