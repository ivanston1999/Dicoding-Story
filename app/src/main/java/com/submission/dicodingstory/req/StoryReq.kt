package com.submission.dicodingstory.req


data class StoryReq(
    val description: String,
    val lat: Float? = null,
    val lon: Float? = null
)