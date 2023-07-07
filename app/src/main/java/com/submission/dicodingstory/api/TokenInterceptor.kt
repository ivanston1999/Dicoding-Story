package com.submission.dicodingstory.api

import com.submission.dicodingstory.util.AccountPref
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val pref: AccountPref): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        pref.getAcc().token.let{

            requestBuilder.addHeader("Authorization", "Bearer $it")}
        return chain.proceed(requestBuilder.build())}
}