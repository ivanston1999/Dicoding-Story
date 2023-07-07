package com.submission.dicodingstory.error

import com.submission.dicodingstory.response.BaseResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class ResponseHandler @Inject constructor(private val retrofit: Retrofit) {

    fun converterBaseError(error: ResponseBody): BaseResponse?
    {
        val converter: Converter<ResponseBody, BaseResponse> = retrofit

            .responseBodyConverter(BaseResponse::class.java, arrayOfNulls<Annotation>(0))

        return converter.convert(error)}

}