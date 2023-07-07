package com.submission.dicodingstory.error

data class DataResult<out T>(val status: OperationStatus, val data: T?, val message: String?){
    companion object

    {

        fun <T> loading(data: T?) = DataResult(OperationStatus.LOADING, data, null)

        fun <T> success(data: T?): DataResult<T> = DataResult(OperationStatus.SUCCESS, data, null)


        fun <T> error(message: String?, data: T) = DataResult(OperationStatus.ERROR, data, message)



    }
}