package com.submission.dicodingstory.error

import com.submission.dicodingstory.response.BaseResponse
import com.submission.dicodingstory.util.Default.UNCERTAIN
import retrofit2.Response
import com.submission.dicodingstory.util.Default.TIMEOUT
import okhttp3.ResponseBody
import java.net.SocketTimeoutException

class NetworkHandler {
    suspend fun <T> enqueue(
        converter: (ResponseBody)
        -> BaseResponse?,
        call: suspend ()
        -> Response<T>
    ):
            DataResult<T> =
        try

        {

            val response = call()


            val errorResponseBody = response.errorBody()


            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null)

            {
                DataResult.success(responseBody)
            }

            else if (errorResponseBody != null)

            {
                val errorConverted = converter(errorResponseBody)
                DataResult.error(errorConverted?.message?.toString(), null)
            }

            else

            {
                DataResult.error(UNCERTAIN, null)
            }
        }
        catch (e: Exception)
        {
            when (e)

            {
                is SocketTimeoutException -> DataResult.error(TIMEOUT, null)
                else -> DataResult.error(UNCERTAIN, null)}}

    suspend fun <T, R>
                enqueue(
        req: T,
        converter: (ResponseBody)

        ->
        BaseResponse?,

        call: suspend (T)

        ->
        Response<R>
    ):

            DataResult<R> =
        try

        {
            val response = call(req)
            val errorResponseBody = response.errorBody()

            val responseBody = response.body()


            if
                    (response.isSuccessful && responseBody != null)
            {
                DataResult.success(responseBody)
            }
            else if
                    (errorResponseBody != null)
            {
                val parsedError = converter(errorResponseBody)
                DataResult.error(parsedError?.message.toString(), null)
            }
            else
            {
                DataResult.error(UNCERTAIN, null)}}


        catch
            (e: Exception)
        {
            when
                    (e)
            {
                is SocketTimeoutException -> DataResult.error(TIMEOUT, null)
                else -> DataResult.error(UNCERTAIN, null)}}


    suspend fun <T, U, S, V, R> enqueue
                (
        req1: T, req2: U, req3: S, req4: V,
        converter: (ResponseBody)
        -> BaseResponse?,
        call: suspend (T, U, S, V)
        -> Response<R>
    )
    : DataResult<R> =
        try
        {
            val response = call(req1, req2, req3, req4)

            val errorResponseBody = response.errorBody()


            val responseBody = response.body()

            if
                    (response.isSuccessful && responseBody != null)
            {
                DataResult.success(responseBody)}

            else if (errorResponseBody != null)
            {
                val parsedError = converter(errorResponseBody)
                DataResult.error(parsedError?.message.toString(), null)
            }

            else

            {
                DataResult.error(UNCERTAIN, null)}
        }
        catch

            (e: Exception)

        {
            when (e)


            {
                is SocketTimeoutException -> DataResult.error(TIMEOUT, null)
                else -> DataResult.error(UNCERTAIN, null)}}

}
