package com.submission.dicodingstory.response

import com.submission.dicodingstory.model.Account
data class AccountResponse(
    val error: Boolean,
    val message: String,
    val loginResult: Account,
)